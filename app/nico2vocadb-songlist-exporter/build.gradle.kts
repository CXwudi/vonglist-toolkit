plugins {
  id("my.kotlin-spring-app")
}

dependencies {
  implementation(project(":shared:all-model"))
  implementation(project(":core:nico2vocadb-songlist-exporter"))
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-json")
  implementation("org.springframework:spring-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}