plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  api(platform("org.springframework.boot:spring-boot-dependencies"))
  implementation("com.github.CXwudi:kotlin-jvm-inline-logging")
  api("org.apache.httpcomponents.client5:httpclient5")
}