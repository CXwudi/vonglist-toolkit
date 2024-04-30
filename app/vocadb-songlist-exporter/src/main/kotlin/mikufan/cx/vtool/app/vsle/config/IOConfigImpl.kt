package mikufan.cx.vtool.app.vsle.config

import mikufan.cx.vtool.core.vsle.config.IOConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import java.nio.file.Path

@ConfigurationProperties(prefix = "io")
data class IOConfigImpl(
  override val vocadbListId: Long,
  override val outputCsv: Path
) : IOConfig
