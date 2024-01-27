package mikufan.cx.vtool.service.impl

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.module.model.vocadb.PvService
import mikufan.cx.vtool.service.api.cache.KVCache
import java.nio.file.Path
import java.util.*

class PvToVocaDbIdCachePersistor(
  private val cache: KVCache<PV, Long>,
  private val csvFile: Path,
) {

  val csvMapper = CsvMapper()
  val csvSchema = CsvSchema.builder()
    .addColumnsFrom(csvMapper.schemaFor(PV::class.java))
    .addColumn("vocadbId")
    .setUseHeader(true)
    .build()

  fun persist() {
    val toWrite: List<List<String>> = cache.allMappings().map { (pv, idNullable) ->
      buildList {
        add(pv.pvId)
        add(pv.pvService.toString())
        idNullable.ifPresent { add(it.toString()) }
      }
    }
    val writer = csvMapper.writer(csvSchema)
    writer.writeValues(csvFile.toFile()).writeAll(toWrite)
  }

  fun read() {
    val firstColName = csvSchema.column(0).name
    val secondColName = csvSchema.column(1).name
    val thirdColName = csvSchema.column(2).name
    val reader = csvMapper.reader(csvSchema).withFeatures(
      CsvParser.Feature.ALLOW_COMMENTS,
      CsvParser.Feature.SKIP_EMPTY_LINES,
      CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE,
      CsvParser.Feature.TRIM_SPACES,
      CsvParser.Feature.INSERT_NULLS_FOR_MISSING_COLUMNS
    )

    val mapToSave: Map<PV, Optional<Long?>> = reader.readValues<Map<String, String>>(csvFile.toFile())
      .asSequence().associate {
        val pvId = requireNotNull(it[firstColName]) { "pvId should not be null" }
        val pvServiceStr = requireNotNull(it[secondColName]) { "pvService should not be null" }
        val vocadbId: Long? = if (it[thirdColName] in listOf(null, "")) null else it[thirdColName]!!.toLong()
        PV(pvId, PvService.fromString(pvServiceStr)) to (Optional.ofNullable(vocadbId) as Optional<Long?>)
      }

    cache.setAllMappings(mapToSave)
  }

}