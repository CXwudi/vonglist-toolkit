plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  implementation(project(":shared:all-model"))
  api(platform("org.springframework.boot:spring-boot-dependencies"))
  api(project(":component:io-impl"))
  api("org.springframework.boot:spring-boot")
  api("com.github.CXwudi:kotlin-jvm-inline-logging")
  api("org.springframework:spring-web")
  api("org.apache.httpcomponents.client5:httpclient5")
  api("jakarta.validation:jakarta.validation-api")
}