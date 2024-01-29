package mikufan.cx.vtool.core.n2vex

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.module.model.vocadb.PvService
import mikufan.cx.vtool.service.api.cache.KVCache
import mikufan.cx.vtool.service.api.cache.KVCachePersistor
import mikufan.cx.vtool.service.impl.InVmKVCache
import java.nio.file.Path
import java.util.*
import kotlin.io.path.notExists

class PvToVocaDbIdCachePersistor(
  private val csvFile: Path,
) : KVCachePersistor<PV, Long> {

  val csvMapper = CsvMapper()
  val csvSchema = CsvSchema.builder()
    .addColumnsFrom(csvMapper.schemaFor(PV::class.java))
    .addColumn("vocadbId")
    .setUseHeader(true)
    .build()

  override fun persist(cache: KVCache<PV, Long>) {
    val toWrite: List<List<String>> = cache.allMappings().map { (pv, idNullable) ->
      buildList {
        add(pv.pvId)
        add(pv.pvService.toString())
        add(pv.pvTitle)
        idNullable.ifPresent { add(it.toString()) }
      }
    }
    val writer = csvMapper.writer(csvSchema)
    writer.writeValues(csvFile.toFile()).use {
      it.writeAll(toWrite)
    }
  }

  override fun load(): KVCache<PV, Long> {
    if (csvFile.notExists() || csvFile.toFile().length() == 0L) {
      return InVmKVCache()
    }
    val firstColName = csvSchema.column(0).name
    val secondColName = csvSchema.column(1).name
    val thirdColName = csvSchema.column(2).name
    val fourthColName = csvSchema.column(3).name
    val reader = csvMapper.readerForMapOf(String::class.java)
      .with(csvSchema)
      .withFeatures(
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
        val pvTitle = it[thirdColName] ?: ""
        val vocadbId: Long? = if (it[fourthColName] in listOf(null, "")) null else it[fourthColName]!!.toLong()
        PV(pvId, PvService.fromString(pvServiceStr), pvTitle) to (Optional.ofNullable(vocadbId) as Optional<Long?>)
      }
    return InVmKVCache<PV, Long>().apply { setAllMappings(mapToSave) }
  }
}