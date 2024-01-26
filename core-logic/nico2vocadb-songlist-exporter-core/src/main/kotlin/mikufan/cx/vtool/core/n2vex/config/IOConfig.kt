package mikufan.cx.vtool.core.n2vex.config

import jakarta.validation.constraints.Positive
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists
import kotlin.properties.Delegates

class IOConfig {
  @get:Positive
  var nicoListId: Long = -1
  lateinit var outputFile: Path
  lateinit var errorCsv: Path

  fun createFolders(): Unit {
    if (outputFile.parent.notExists()) {
      outputFile.parent.createDirectories()
    }
    if (errorCsv.parent.notExists()) {
      errorCsv.parent.createDirectories()
    }
  }

}
