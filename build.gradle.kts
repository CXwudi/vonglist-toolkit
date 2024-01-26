plugins {
  // This is to resolve `The Kotlin Gradle plugin was loaded multiple times` warning
  // see https://youtrack.jetbrains.com/issue/KT-46200/False-positive-for-The-Kotlin-Gradle-plugin-was-loaded-multiple-times-when-applied-kotlin-and-jvm-plugins-in-different-modules#focus=Comments-27-4850274.0-0
  // Another option is to set kotlin.pluginLoadedInMultipleProjects.ignore=true in gradle.properties file, but it's not recommended
  alias(libs.plugins.kotlinJvm) apply false
}
