plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  implementation(platform("org.springframework.boot:spring-boot-dependencies"))
  api(project(":component:io-api"))
  api(project(":shared:all-model"))
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
}
