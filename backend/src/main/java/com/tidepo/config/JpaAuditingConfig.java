package com.tidepo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** JPA Auditing（@CreatedDate / @LastModifiedDate の自動設定）を有効化する。 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {}
