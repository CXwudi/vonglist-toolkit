import kotlin.io.path.isDirectory
import kotlin.io.path.isHidden
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

pluginManagement {
  repositories {
    gradlePluginPortal()
  }
  includeBuild("gradle/plugins") // use this to include our own convention plugins
  includeBuild("gradle/settings") // use this to include our own convention plugins for settings.gradle.kts
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
    maven("https://jitpack.io")
  }

  includeBuild("gradle/platform") // use this to include our own gradle platform to centralize version management
  // gradle/libs.versions.toml is automatically imported if exists
}

plugins {
  // my setting plugin that simply has some other setting plugins where versions are managed in version catalogs
  id("my.root-settings-plugins")
}

develocity {
  buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    termsOfUseAgree = "yes"
  }
}

rootProject.name = "Vocaloid SongList Toolkit"

val layers = listOf("shared", "component", "business-logic", "app", "util")

for (layer in layers) {
  rootDir.toPath().resolve(layer).listDirectoryEntries()
    .filter { it.isDirectory() && !it.isHidden() && it.listDirectoryEntries("{build,settings}.{gradle,gradle.kts}").any() }
    .forEach { include(":$layer:${it.name}") }
}

