plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  api(project(":util:apache-httpclient-util-api"))
  api("com.github.CXwudi:kotlin-jvm-inline-logging")
}