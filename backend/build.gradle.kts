plugins {
  java
  id("org.springframework.boot") version "4.1.0"
  id("io.spring.dependency-management") version "1.1.7"
  id("com.diffplug.spotless") version "7.0.4"
  checkstyle
}

group = "com.tidepo"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-webmvc")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  // マイグレーション（Flyway がスキーマを所有し、JPA は検証のみ ddl-auto=validate）
  // Spring Boot 4 は autoconfig がモジュール分割されており、Flyway の自動設定は
  // spring-boot-flyway モジュールに入る（flyway-core だけでは有効化されない）
  implementation("org.springframework.boot:spring-boot-flyway")
  implementation("org.flywaydb:flyway-core")
  implementation("org.flywaydb:flyway-database-postgresql")
  // 変更履歴（監査）
  implementation("org.hibernate.orm:hibernate-envers")
  runtimeOnly("org.postgresql:postgresql")
  // 開発時のみ有効。ソース再コンパイル時に bootRun を自動再起動する（Docker 開発向け）
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
  testImplementation("com.tngtech.archunit:archunit-junit5:1.4.1")
  // Testcontainers（テスト時に使い捨ての本物 PostgreSQL を Docker で起動）
  // Spring Boot 4 の BOM は testcontainers を管理しないため、Testcontainers BOM を import する
  testImplementation(platform("org.testcontainers:testcontainers-bom:1.21.3"))
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:postgresql")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// 整形（formatter）: palantir-java-format は 4 スペース。.editorconfig（Java=4）と整合させる。
spotless {
  java {
    target("src/**/*.java")
    palantirJavaFormat()
    removeUnusedImports()
    trimTrailingWhitespace()
    endWithNewline()
  }
}

// 静的解析（formatter と競合しない最小ルールのみ。整形は spotless が担当）
checkstyle {
  toolVersion = "10.26.1"
  configFile = file("config/checkstyle/checkstyle.xml")
  isIgnoreFailures = false
}

tasks.withType<Test> {
  useJUnitPlatform()
}
