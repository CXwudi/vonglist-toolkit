plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  implementation(project(":shared:all-model"))
  api(platform("org.springframework.boot:spring-boot-dependencies"))
  api(project(":component:io-api"))
  api(project(":component:http-service-api"))
  api("org.springframework.boot:spring-boot")
  api("org.springframework:spring-web")
  api("com.github.CXwudi:kotlin-jvm-inline-logging")
}