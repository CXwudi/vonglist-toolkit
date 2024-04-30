plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  api(platform("org.springframework.boot:spring-boot-dependencies"))
  // https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api
  implementation("jakarta.validation:jakarta.validation-api")
}