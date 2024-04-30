package mikufan.cx.vtool.core.vsle.config

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

interface IOConfig {
  val vocadbSongListId: Long
  val outputCsv: Path

  fun createFolders() {
    if (outputCsv.parent?.notExists() == true) {
      outputCsv.parent?.createDirectories()
    }
  }
}