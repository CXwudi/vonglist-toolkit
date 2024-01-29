package mikufan.cx.vtool.service.impl

import com.fasterxml.jackson.databind.SequenceWriter
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import mikufan.cx.vtool.service.api.io.ItemRecorder
import java.io.Closeable
import java.nio.file.Path

class ToCsvItemRecorder<T>(
  csvFile: Path,
  clazz: Class<T>
) : ItemRecorder<T>, Closeable {

  private val writer: SequenceWriter

  init {
    val csvMapper = CsvMapper()
    val schema: CsvSchema = csvMapper
      .schemaFor(clazz)
      .withHeader()
      .withColumnSeparator(',')
    writer = csvMapper.writer(schema).writeValues(csvFile.toFile())
  }

  override fun recordAll(items: Iterable<T>) {
    writer.use {
      it.writeAll(items)
    }
  }

  override fun record(item: T) {
    writer.write(item)
  }

  override fun close() {
    writer.close()
  }
}

inline fun <reified T> ToCsvItemRecorder(
  csvFile: Path
) = ToCsvItemRecorder(csvFile, T::class.java)
