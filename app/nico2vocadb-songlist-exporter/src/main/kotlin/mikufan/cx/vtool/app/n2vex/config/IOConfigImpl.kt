package mikufan.cx.vtool.app.n2vex.config

import mikufan.cx.vtool.core.n2vex.config.IOConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.nio.file.Path

@ConfigurationProperties(prefix = "io")
@Validated
data class IOConfigImpl(
  override val nicoListId: Long,
  override val outputCsv: Path,
  override val notFoundCsv: Path
) : IOConfig {
  init {
    createFolders()
  }
}
