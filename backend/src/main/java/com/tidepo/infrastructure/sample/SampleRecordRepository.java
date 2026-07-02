package com.tidepo.infrastructure.sample;

import org.springframework.data.jpa.repository.JpaRepository;

/** サンプルエンティティ用の Spring Data リポジトリ（基盤実証用）。 */
public interface SampleRecordRepository extends JpaRepository<SampleRecord, Long> {}
