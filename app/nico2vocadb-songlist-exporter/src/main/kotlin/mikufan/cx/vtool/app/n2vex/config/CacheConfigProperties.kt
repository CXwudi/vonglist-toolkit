package mikufan.cx.vtool.app.n2vex.config

import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import java.nio.file.Path
import java.time.Duration
import kotlin.io.path.absolute
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

@ConfigurationProperties(prefix = "system.cache")
data class CacheConfigProperties(
  val dir: Path,
  val ttl: Duration,
) {

  @PostConstruct
  fun createDir() {
    val parent = dir.absolute().parent
    if (parent.notExists()) {
      parent.createDirectories()
    }
  }
}