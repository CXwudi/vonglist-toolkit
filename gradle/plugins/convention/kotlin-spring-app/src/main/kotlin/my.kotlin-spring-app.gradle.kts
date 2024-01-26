plugins {
  id("my.mixin.kotlin-jvm")
  // although these two mixin only have one usage here, which can be inlined,
  // but I prefer to keep them in separate modules for clarity
  id("my.mixin.app")
  id("my.mixin.spring-app")
}

