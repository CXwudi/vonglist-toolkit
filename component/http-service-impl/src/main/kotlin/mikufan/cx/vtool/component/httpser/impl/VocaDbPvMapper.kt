package mikufan.cx.vtool.component.httpser.impl

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.httpser.api.PvMapper
import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongByPvApi
import mikufan.cx.vtool.shared.model.vocadb.PV
import org.springframework.cache.Cache

class VocaDbPvMapper(
  private val songByPvApi: VocaDbSongByPvApi,
  private val cache: Cache
) : PvMapper {

  override fun tryFindRecord(pv: PV): Long? {
    val (pvId, pvService, _) = pv
    val cached = cache[pv.toKey()]
    if (cached != null) {
      log.info { "Found cached VocaDB entry for $pv" }
      return cached.get() as Long?
    } else {
      log.info { "Trying to fetch VocaDB entry for $pv from VocaDB API" }
    }
    val result = songByPvApi.getSongByPv(pvId, pvService)
    return if (result == null || result.isNull) {
      log.warn { "No VocaDB entry found for $pv" }
      handleCache(pv, null)
      null
    } else {
      val vocadbId = result["id"].asLong()
      handleCache(pv, vocadbId)
      vocadbId
    }

  }

  private fun PV.toKey() = "$pvId@$pvService"

  private fun handleCache(pv: PV, vocadbId: Long?) {
    cache.put(pv.toKey(), vocadbId)
  }
}

private val log = KInlineLogging.logger()