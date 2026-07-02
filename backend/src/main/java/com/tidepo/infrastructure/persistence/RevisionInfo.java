package com.tidepo.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 * Envers のリビジョン台帳エンティティ。
 *
 * <p>既定の生成戦略（シーケンス）に依存すると Flyway 側のスキーマと一致させにくいため、
 * ID を IDENTITY にして revinfo テーブル（rev = identity, revtstmp）と素直に対応させる。
 */
@Entity
@RevisionEntity
@Table(name = "revinfo")
public class RevisionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "rev")
    private long rev;

    @RevisionTimestamp
    @Column(name = "revtstmp")
    private long revtstmp;

    public long getRev() {
        return rev;
    }

    public long getRevtstmp() {
        return revtstmp;
    }
}
