package mikufan.cx.vtool.app.vsli.config

import jakarta.validation.constraints.Positive
import mikufan.cx.vtool.core.vsli.config.IOConfig
import mikufan.cx.vtool.util.validation.FileReadable
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.nio.file.Path

@ConfigurationProperties(prefix = "io")
@Validated
data class IOConfigImpl(
  @get:Positive
  override val vocaDbListId: Long?,
  @get:FileReadable
  override val inputCsv: Path,
) : IOConfig
