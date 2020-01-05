package com.study.springbatch.entity;

import com.study.springbatch.type.BatchStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "app_batch_record")
public class BatchRecord {
    @Id
    @Column(name = "pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BatchStatus status;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdDateTime;


    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    public BatchRecord(long id) {
        this.id = id;
        this.status = BatchStatus.READY;
        this.createdDateTime = LocalDateTime.now();
        this.updatedDateTime = this.createdDateTime;
    }
}
