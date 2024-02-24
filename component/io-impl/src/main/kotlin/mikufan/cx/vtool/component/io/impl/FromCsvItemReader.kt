package mikufan.cx.vtool.component.io.impl

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import mikufan.cx.vtool.component.io.api.ItemReader
import java.nio.file.Path

/**
 * From csv item reader
 *
 * @param T the type of the item
 *
 * @param csvFile the path to the csv file
 * @param withHeader whether the csv file has a header
 * @param clazz the class of the item
 */
class FromCsvItemReader<T>(
  csvFile: Path,
  withHeader: Boolean = true,
  clazz: Class<T>
) : ItemReader<T> {

  private val iterator: Iterator<T>

  init {
    val kotlinModule = KotlinModule.Builder().build()
    val csvMapper = CsvMapper()
    csvMapper.registerModule(kotlinModule)
    val schema: CsvSchema = csvMapper
      .schemaFor(clazz)
      .let { if (withHeader) it.withHeader() else it }
      .withColumnSeparator(',')
    iterator = csvMapper.readerFor(clazz).with(schema).withFeatures(
      CsvParser.Feature.ALLOW_COMMENTS,
      CsvParser.Feature.SKIP_EMPTY_LINES,
      CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE,
      CsvParser.Feature.TRIM_SPACES,
    ).readValues(csvFile.toFile())
  }

  override fun read(): T? = if (iterator.hasNext()) iterator.next() else null

}

/**
 * Create a FromCsvItemReader with the reified type
 */
inline fun <reified T> FromCsvItemReader(
  csvFile: Path,
  withHeader: Boolean = true
) = FromCsvItemReader(csvFile, withHeader, T::class.java)