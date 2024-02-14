package mikufan.cx.vtool.component.io.impl

import com.fasterxml.jackson.databind.SequenceWriter
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import mikufan.cx.vtool.component.io.api.ItemRecorder
import java.io.Closeable
import java.nio.file.Path

class ToCsvItemRecorder<T>(
  csvFile: Path,
  withHeader: Boolean = true,
  clazz: Class<T>
) : ItemRecorder<T>, Closeable {

  private val writer: SequenceWriter

  init {
    val csvMapper = CsvMapper()
    val schema: CsvSchema = csvMapper
      .schemaFor(clazz)
      .let { if (withHeader) it.withHeader() else it }
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
  csvFile: Path,
  withHeader: Boolean = true
) = ToCsvItemRecorder(csvFile, withHeader, T::class.java)
