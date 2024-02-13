plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  api(platform("org.springframework.boot:spring-boot-dependencies"))
  api(project(":component:io-api"))
  api(project(":shared:all-model"))
  api("com.fasterxml.jackson.core:jackson-databind")
  api("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
}
