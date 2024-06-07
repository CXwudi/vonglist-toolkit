plugins {
  id("my.kotlin-jvm-lib")
}

dependencies {
  api(platform("org.springframework.boot:spring-boot-dependencies"))
  api("org.infinispan:infinispan-core")
  api("org.infinispan:infinispan-jcache")
}
