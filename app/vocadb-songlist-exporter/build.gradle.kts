plugins {
  id("my.kotlin-spring-app")
}

dependencies {
  implementation(project(":shared:all-model"))
  implementation(project(":business-logic:vocadb-songlist-exporter"))
  implementation(project(":component:io-impl"))
  implementation(project(":component:http-service-impl"))
  implementation(project(":util:apache-httpclient-util"))
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-json")
  implementation("org.springframework:spring-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

application {
  mainClass = "mikufan.cx.vtool.app.vsle.MainKt"
}
