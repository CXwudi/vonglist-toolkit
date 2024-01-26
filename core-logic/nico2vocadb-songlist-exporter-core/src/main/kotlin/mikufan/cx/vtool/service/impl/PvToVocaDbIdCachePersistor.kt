package mikufan.cx.vtool.service.impl

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.module.model.vocadb.PvService
import mikufan.cx.vtool.service.api.cache.PvToVocaDbIdCache
import mikufan.cx.vtool.service.api.cache.ExternalCachePersistor
import java.nio.file.Path
import java.util.*

class PvToVocaDbIdCachePersistor(
  private val cache: PvToVocaDbIdCache,
  private val csvFile: Path,
) : ExternalCachePersistor {

  val csvMapper = CsvMapper()
  val csvSchema = CsvSchema.builder()
    .addColumnsFrom(csvMapper.schemaFor(PV::class.java))
    .addColumn("vocadbId")
    .setUseHeader(true)
    .build()

  override fun persist() {
    val toWrite: List<List<String?>> = cache.allMappings().map { (pv, idNullable) ->
      listOf(pv.pvId, pv.pvService.toString(), idNullable.orElse(null)?.toString())
    }
    val writer = csvMapper.writer(csvSchema)
    writer.writeValues(csvFile.toFile()).writeAll(toWrite)
  }

  override fun read() {
    val firstColName = csvSchema.column(0).name
    val secondColName = csvSchema.column(1).name
    val thirdColName = csvSchema.column(2).name
    val reader = csvMapper.reader(csvSchema).withFeatures(
      CsvParser.Feature.ALLOW_COMMENTS,
      CsvParser.Feature.SKIP_EMPTY_LINES,
      CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE,
      CsvParser.Feature.TRIM_SPACES
    )

    val mapToSave: Map<PV, Optional<Long?>> = reader.readValues<Map<String, String>>(csvFile.toFile())
      .asSequence().associate {
        val pvId = requireNotNull(it[firstColName]) { "pvId should not be null" }
        val pvServiceStr = requireNotNull(it[secondColName]) { "pvService should not be null" }
        val vocadbId = it[thirdColName]?.toLong()
        PV(pvId, PvService.fromString(pvServiceStr)) to (Optional.ofNullable(vocadbId) as Optional<Long?>)
      }

    cache.setAllMappings(mapToSave)
  }

}