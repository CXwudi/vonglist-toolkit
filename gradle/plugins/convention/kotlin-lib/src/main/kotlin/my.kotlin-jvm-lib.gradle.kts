plugins {
  id("my.mixin.kotlin-jvm")
  // although a mixin with only one usage can be inlined here,
  // but for this lib mixin, I prefer to keep it in a separate file
  id("my.mixin.lib")
}
