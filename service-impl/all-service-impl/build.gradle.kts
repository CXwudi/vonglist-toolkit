plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  implementation(project(":service-api:all-service-api"))
}