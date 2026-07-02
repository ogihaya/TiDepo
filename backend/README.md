# backend

TiDepo のバックエンド。

## スタック

- 言語/FW: Java 21 / Spring Boot 4.x
- アーキテクチャ: オニオンアーキテクチャ（単一モジュール＋ArchUnit で依存方向を検証）
- ビルド: Gradle（Kotlin DSL）
- 整形/静的解析: Spotless（palantir-java-format）/ Checkstyle
- テスト: JUnit 5 / Spring Boot Test / ArchUnit / Testcontainers
- DB: PostgreSQL / Flyway（マイグレーション）/ JPA・Hibernate / JPA Auditing / Hibernate Envers（変更履歴）
- ベースパッケージ: `com.tidepo`

## ディレクトリ（オニオン層）

```text
src/main/java/com/tidepo/
  domain/          # ドメイン（純粋ロジック。他層・FWに非依存）
  application/     # ユースケース（domain のみ依存）
  infrastructure/  # リポジトリ実装・外部連携
  presentation/    # REST コントローラ・DTO
  config/          # DI 配線・設定
  TidepoApplication.java
```

層の依存方向は `src/test/java/com/tidepo/architecture/OnionArchitectureTest.java`（ArchUnit）で検証する。

## よく使うコマンド

`backend/` ディレクトリで実行する（Gradle Wrapper を使用）。

```bash
./gradlew spotlessApply   # コード整形
./gradlew check           # 整形チェック + Checkstyle + テスト（ArchUnit 含む）
./gradlew test            # テスト
./gradlew build           # ビルド（check 含む）
./gradlew bootRun         # アプリ起動（http://localhost:8080）
```

## 動作確認

起動後、ヘルスチェック:

```bash
curl http://localhost:8080/api/health
# => {"status":"UP"}
```

## データベース

- スキーマは **Flyway が所有**する。マイグレーションは `src/main/resources/db/migration/`（`V1__init.sql` …）。JPA は `ddl-auto=validate` でエンティティとスキーマの一致を検証するのみ。
- **JPA Auditing**: `created_at` / `updated_at` を自動設定（`AuditableEntity`）。「誰が」を表す項目は認証導入時（Phase 1）に追加。
- **Hibernate Envers**: `@Audited` を付けたエンティティの変更履歴を `*_aud` / `revinfo` に記録。監査テーブルも Flyway で作成する。
- 接続情報は環境変数（`SPRING_DATASOURCE_URL` / `_USERNAME` / `_PASSWORD`）で上書き可。ローカル直起動時の既定は `localhost:5432`。
- 現状の `sample_record`（`infrastructure/sample`）は**基盤実証用の使い捨て**。実エンティティ導入時に置換・削除する。

## テスト（Testcontainers）

- DB を使うテストは **Testcontainers** で使い捨ての PostgreSQL を Docker 上に起動する（`AbstractPostgresContainerTest` がシングルトンで1つ起動し全テストで共有）。
- **Docker が必要**。Docker Desktop なら追加設定不要。**Colima** など標準ソケットが無い環境では、テスト実行時に以下を設定する:

```bash
export DOCKER_HOST="unix://$HOME/.colima/default/docker.sock"
export TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE="/var/run/docker.sock"
```

## メモ

- JDK は Gradle toolchain で Java 21 を要求する（未導入環境では toolchain が解決を試みる）。
- `docker compose up`（リポジトリ直下）で backend は postgres に接続し、起動時に Flyway が適用される。
