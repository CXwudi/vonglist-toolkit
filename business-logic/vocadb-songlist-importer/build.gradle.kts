plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  implementation(project(":shared:all-model"))
  api("com.github.CXwudi:kotlin-jvm-inline-logging")
}