plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  implementation(platform("org.springframework.boot:spring-boot-dependencies"))
  api("com.fasterxml.jackson.core:jackson-databind")
}