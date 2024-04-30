package mikufan.cx.vtool.app.vsle.config

import jakarta.validation.constraints.Positive
import mikufan.cx.vtool.core.vsle.config.IOConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.nio.file.Path

@ConfigurationProperties(prefix = "io")
@Validated
data class IOConfigImpl(
  @get:Positive
  override val vocadbListId: Long,
  override val outputCsv: Path
) : IOConfig
