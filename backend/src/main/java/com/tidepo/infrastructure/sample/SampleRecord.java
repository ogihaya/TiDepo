package com.tidepo.infrastructure.sample;

import com.tidepo.infrastructure.persistence.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

/**
 * Phase 0-5 の基盤実証用サンプルエンティティ。
 *
 * <p>Flyway＋JPA＋JPA Auditing＋Envers＋Testcontainers が動くことを確認するための使い捨て。
 * 実業務エンティティ（User/Task 等）が導入される Phase 1 以降で置換・削除する。
 */
@Entity
@Audited
@Table(name = "sample_record")
public class SampleRecord extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    protected SampleRecord() {
        // JPA 用
    }

    public SampleRecord(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
