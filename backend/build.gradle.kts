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
  // 開発時のみ有効。ソース再コンパイル時に bootRun を自動再起動する（Docker 開発向け）
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
  testImplementation("com.tngtech.archunit:archunit-junit5:1.4.1")
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
