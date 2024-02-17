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
    log.info { "Trying to map VocaDB entry for $pv" }
    val (pvId, pvService, _) = pv
    val cached = cache[pv.toKey()]
    if (cached != null) {
      return cached.get() as Long?
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