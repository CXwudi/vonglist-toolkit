package mikufan.cx.vtool.service.impl

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.service.api.cache.PvToVocaDbIdCache
import mikufan.cx.vtool.service.api.VocaDbPvMapper
import mikufan.cx.vtool.service.api.api.VocaDbSongByPvApi

class ApiBasedVocaDbPvMapper(
  private val songByPvApi: VocaDbSongByPvApi,
  private val cache: PvToVocaDbIdCache,
) : VocaDbPvMapper {

  override fun tryFindRecord(pv: PV): Long? {
    log.info { "Trying to map VocaDB entry for $pv" }
    val (pvId, pvService, _) = pv
    val cached = cache.tryFindMapping(pv)
    if (cached != null) {
      return cached.orElse(null)
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

  private fun handleCache(pv: PV, vocadbId: Long?) {
    cache.recordMapping(pv, vocadbId)
  }
}

private val log = KInlineLogging.logger()
