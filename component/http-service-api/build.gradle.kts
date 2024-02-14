plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  api(project(":shared:all-model"))
  api("org.apache.httpcomponents.client5:httpclient5")
}