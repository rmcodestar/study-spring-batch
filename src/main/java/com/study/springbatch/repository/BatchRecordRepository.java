package com.study.springbatch;

import com.study.springbatch.entity.BatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRecordRepository extends JpaRepository<BatchRecord, Long> {
}
