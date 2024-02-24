package mikufan.cx.vtool.core.vsli.config

import java.nio.file.Path

interface IOConfig {
  val vocaDbListId: Long?
  val inputCsv: Path
}