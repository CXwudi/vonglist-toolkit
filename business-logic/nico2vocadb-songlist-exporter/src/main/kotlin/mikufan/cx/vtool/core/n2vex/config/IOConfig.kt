package mikufan.cx.vtool.core.n2vex.config

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

interface IOConfig {
  val nicoListId: Long
  val outputCsv: Path
  val notFoundCsv: Path

  fun createFolders() {
    if (outputCsv.parent?.notExists() == true) {
      outputCsv.parent?.createDirectories()
    }
    if (notFoundCsv.parent?.notExists() == true) {
      notFoundCsv.parent?.createDirectories()
    }
  }

}
