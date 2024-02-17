plugins {
  id("my.kotlin-spring-app")
}

dependencies {
  implementation(project(":shared:all-model"))
  implementation(project(":core:nico2vocadb-songlist-exporter"))
  implementation(project(":component:io-impl"))
  implementation(project(":component:http-service-impl"))
  implementation(project(":component:apache-httpclient-util-impl"))
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-json")
  implementation("org.springframework.boot:spring-boot-starter-cache")
  implementation("org.infinispan:infinispan-spring-boot3-starter-embedded")
  implementation("org.infinispan:infinispan-core-jakarta")
  implementation("org.infinispan:infinispan-jcache")
  implementation("javax.cache:cache-api")
  implementation("org.springframework:spring-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}