plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  api(project(":component:apache-httpclient-util-api"))
  api("com.github.CXwudi:kotlin-jvm-inline-logging")
}