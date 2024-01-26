package mikufan.cx.vtool.service.impl

import com.fasterxml.jackson.databind.SequenceWriter
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import mikufan.cx.vtool.module.model.niconico.NicoListItem
import mikufan.cx.vtool.service.api.SongNotFoundNicoPvRecorder
import java.nio.file.Path

class ToCsvSongNotFoundPvRecorder(
  csvFile: Path,
) : SongNotFoundNicoPvRecorder {

  private val writer: SequenceWriter

  init {
    val csvMapper = CsvMapper()
    val schema: CsvSchema = csvMapper
      .schemaFor(NicoListItem::class.java)
      .withHeader()
      .withColumnSeparator(',')
    writer = csvMapper.writer(schema).writeValues(csvFile.toFile())
  }

  override fun recordAll(items: Iterable<NicoListItem>) {
    writer.use {
      it.writeAll(items)
    }
  }

  override fun record(item: NicoListItem) {
    throw UnsupportedOperationException("not implemented")
  }
}
