package mikufan.cx.vtool.component.io.impl

import mikufan.cx.vtool.component.io.api.ItemRecorder
import mikufan.cx.vtool.module.model.vocadb.VocaDBSongListItem
import java.io.Closeable
import java.nio.file.Path
import kotlin.io.path.bufferedWriter

class VocaDbCompatibleSongListCsvProducer(
  path: Path
) : ItemRecorder<VocaDBSongListItem>, Closeable {

  private val writer = path.bufferedWriter()
  private var counter = 1

  override fun record(item: VocaDBSongListItem) {
    writer.write("${counter++},${item.id},${item.note}")
    writer.newLine()
  }

  override fun close() {
    writer.close()
  }
}