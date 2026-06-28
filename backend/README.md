# backend

TiDepo のバックエンド。

## スタック

- 言語/FW: Java 21 / Spring Boot 4.x
- アーキテクチャ: オニオンアーキテクチャ（単一モジュール＋ArchUnit で依存方向を検証）
- ビルド: Gradle（Kotlin DSL）
- 整形/静的解析: Spotless（palantir-java-format）/ Checkstyle
- テスト: JUnit 5 / Spring Boot Test / ArchUnit
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

## メモ

- JDK は Gradle toolchain で Java 21 を要求する（未導入環境では toolchain が解決を試みる）。
- DB（PostgreSQL / Flyway / JPA・監査）は手順 0-5 で追加する。本手順では未導入。
