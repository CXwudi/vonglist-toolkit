plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  api(project(":component:http-service-api"))
  api("com.github.CXwudi:kotlin-jvm-inline-logging")
  implementation(platform("org.springframework.boot:spring-boot-dependencies"))
  api("org.springframework.boot:spring-boot")
  api("org.springframework:spring-web")
  api("jakarta.validation:jakarta.validation-api")
}