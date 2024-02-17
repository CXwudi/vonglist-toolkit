package my.mixin

plugins {
  id("my.root.jvm")
  id("org.springframework.boot")
  kotlin("plugin.spring")
}

dependencies {
  implementation(platform("org.springframework.boot:spring-boot-dependencies")) // version came from dev-version-constraints platform
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-configuration-processor")

  implementation("com.github.CXwudi:kotlin-jvm-inline-logging")

  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.mockito")
  }
  testImplementation("io.kotest.extensions:kotest-extensions-spring")
  testImplementation("com.ninja-squad:springmockk")
}