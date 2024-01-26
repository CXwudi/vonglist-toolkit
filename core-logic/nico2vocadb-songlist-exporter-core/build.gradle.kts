plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  implementation(project(":shared-module:all-model"))
  api(platform("org.springframework.boot:spring-boot-dependencies"))
  api("com.github.CXwudi:kotlin-jvm-inline-logging")
  api("org.springframework:spring-web")
  api("jakarta.validation:jakarta.validation-api")
  api("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
}