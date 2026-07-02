package com.tidepo.support;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * PostgreSQL を使う統合テスト（{@code @SpringBootTest}）の共通基底クラス。
 *
 * <p>コンテナを「シングルトン」として一度だけ起動し、全テストクラスで再利用する
 * （{@code @Testcontainers}/{@code @Container} を使うとクラスごとに起動・停止され、
 * 複数クラスで共有すると先に停止されてしまうため）。停止は JVM 終了時に Ryuk が行う。
 */
public abstract class AbstractPostgresContainerTest {

    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:17");

    static {
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
