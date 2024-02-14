package mikufan.cx.vtool.app.n2vex.config

import jakarta.annotation.PostConstruct
import jakarta.validation.constraints.Positive
import mikufan.cx.vtool.core.n2vex.config.IOConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.nio.file.Path

@ConfigurationProperties(prefix = "io")
@Validated
data class IOConfigImpl(
  @get:Positive
  override val nicoListId: Long,
  override val outputCsv: Path,
  override val notFoundCsv: Path
) : IOConfig {

  @PostConstruct
  override fun createFolders() {
    super.createFolders()
  }
}
