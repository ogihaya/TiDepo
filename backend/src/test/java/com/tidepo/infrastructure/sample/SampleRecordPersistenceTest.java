package com.tidepo.infrastructure.sample;

import static org.assertj.core.api.Assertions.assertThat;

import com.tidepo.support.AbstractPostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DB 基盤（Flyway・JPA・JPA Auditing・Envers・Testcontainers）が動くことを実証するテスト。
 *
 * <p>共通基底クラスが起動する PostgreSQL コンテナへ接続する。起動時に Flyway が
 * マイグレーションを適用し、ddl-auto=validate がスキーマを検証する。
 */
@SpringBootTest
class SampleRecordPersistenceTest extends AbstractPostgresContainerTest {

    @Autowired
    private SampleRecordRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void save_setsAuditTimestamps() {
        SampleRecord saved = repository.save(new SampleRecord("hello"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
    }

    @Test
    void insertThenUpdate_recordsEnversHistory() {
        SampleRecord saved = repository.save(new SampleRecord("v1")); // insert -> 履歴1件
        saved.setName("v2");
        repository.save(saved); // update -> 履歴2件

        // 他テストと DB を共有するため、対象レコードの id に限定して履歴件数を数える（挿入+更新=2件）
        Long auditRows = jdbcTemplate.queryForObject(
                "select count(*) from sample_record_aud where id = ?", Long.class, saved.getId());
        assertThat(auditRows).isEqualTo(2L);
    }
}
