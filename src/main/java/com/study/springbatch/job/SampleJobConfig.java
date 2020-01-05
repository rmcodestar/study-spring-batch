package com.study.springbatch.job;

import com.study.springbatch.BatchRecordRepository;
import com.study.springbatch.entity.BatchRecord;
import com.study.springbatch.type.BatchStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SampleJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BatchRecordRepository batchRecordRepository;
    private final EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void load() {
        log.info("load data.......");

        List<BatchRecord> batchRecords = Arrays.asList(
                new BatchRecord(1),
                new BatchRecord(2),
                new BatchRecord(3),
                new BatchRecord(4),
                new BatchRecord(5),
                new BatchRecord(6),
                new BatchRecord(7),
                new BatchRecord(8),
                new BatchRecord(9),
                new BatchRecord(10)
        );

        batchRecordRepository.saveAll(batchRecords);
    }

    @Bean
    public Job sampleJob() {
        return jobBuilderFactory.get("sampleJob")
                .start(testStep())
                .build();
    }

    @Bean
    public Step testStep() {
        return stepBuilderFactory.get("testStep")
                .<BatchRecord, BatchRecord>chunk(3)
                .faultTolerant()
                .skipLimit(4)
                .skip(RuntimeException.class)
                .retry(RuntimeException.class)
                .retryLimit(1)
                .reader(batchRecordReader())
                .processor(batchRecordProcessor())
                .writer(batchRecordJpaItemWriter())
                .listener(stepListener())
                .listener(chunkListener())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<BatchRecord> batchRecordReader() {
        return new JpaPagingItemReaderBuilder<BatchRecord>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(2)
                .queryString("SELECT p " +
                        "FROM BatchRecord p " +
                        // "WHERE status = '" + BatchStatus.READY.name() + "' " +  //WARN. pagingReader 쓸 경우 write 하는 조건이 reader 조건에 있으면 결과가 이상해지므로 주의
                        "ORDER BY pk asc")
                .build();

    }

    @Bean
    @StepScope
    public ItemProcessor<BatchRecord, BatchRecord> batchRecordProcessor() {
        return batchRecord -> {
            log.info("[PROCESSOR] {}", batchRecord);


            if (batchRecord.getId() == 6) { ///failed case
                throw new RuntimeException("test");
            }

            batchRecord.setStatus(BatchStatus.DONE);

            return batchRecord;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<BatchRecord> batchRecordJpaItemWriter() {
        return new JpaItemWriterBuilder<BatchRecord>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ChunkListener chunkListener() {
        return new ChunkListener() {
            @Override
            public void beforeChunk(ChunkContext chunkContext) {
                log.info("[CHUNK-before]");
            }

            @Override
            public void afterChunk(ChunkContext chunkContext) {
                int count = chunkContext.getStepContext().getStepExecution().getReadCount();
                log.info("[CHUNK-after] ItemCount: " + count);
            }

            @Override
            public void afterChunkError(ChunkContext chunkContext) {
                log.info("[CHUNK-after-error] summary: " + chunkContext.getStepContext().getStepExecution().getSummary());
            }
        };
    }

    @Bean
    public StepExecutionListener stepListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                log.info("[STEP-before]");
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                int readCount = stepExecution.getReadCount();
                int writeCount = stepExecution.getWriteCount();
                int skipCount = stepExecution.getSkipCount();
                int commitCount = stepExecution.getCommitCount();

                Date start = stepExecution.getStartTime();
                Date end = stepExecution.getEndTime();

                log.info("[STEP-after] readCount : {}, writeCount : {}, skipCount : {}, commitCount : {}, start: {}, end : {}",
                        readCount,
                        writeCount,
                        skipCount,
                        commitCount,
                        start,
                        end
                );

                return null;
            }
        };
    }
}
