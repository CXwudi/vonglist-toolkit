package mikufan.cx.vtool.service.impl

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.service.api.api.VocaDbSongByPvApi
import org.springframework.cache.Cache

class VocaDbPvMapper(
  private val songByPvApi: VocaDbSongByPvApi,
  private val cache: Cache
) {

  fun tryFindRecord(pv: PV): Long? {
    log.info { "Trying to map VocaDB entry for $pv" }
    val (pvId, pvService, _) = pv
    val cached = cache[pv.toKey()]
    if (cached != null) {
      return cached.get() as Long? // TODO: cached.get() isn't the actual value but a wrapper...
    }
    val result = songByPvApi.getSongByPv(pvId, pvService)
    return if (result == null) {
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
