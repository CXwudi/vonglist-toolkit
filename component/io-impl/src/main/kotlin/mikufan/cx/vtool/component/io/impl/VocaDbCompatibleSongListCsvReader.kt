package mikufan.cx.vtool.component.io.impl

import mikufan.cx.vtool.component.io.api.ItemReader
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem
import java.io.Closeable
import java.nio.file.Path
import kotlin.io.path.bufferedReader

class VocaDbCompatibleSongListCsvReader(
  path: Path
) : ItemReader<VocaDBSongListItem>, Closeable {

  private val reader = path.bufferedReader()

  override fun read(): VocaDBSongListItem? {
    val line = reader.readLine() ?: return null
    val parts = line.split(",")
    return VocaDBSongListItem(parts[1].toLong(), parts[2])
  }

  override fun close() {
    reader.close()
  }
}