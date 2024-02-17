plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  implementation(platform("org.springframework.boot:spring-boot-dependencies"))
  api("org.apache.httpcomponents.client5:httpclient5")
}