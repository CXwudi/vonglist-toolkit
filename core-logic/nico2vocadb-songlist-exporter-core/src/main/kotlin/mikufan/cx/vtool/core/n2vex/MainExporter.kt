package mikufan.cx.vtool.core.n2vex

import kotlinx.coroutines.*
import kotlinx.coroutines.Runnable
import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.core.n2vex.config.IOConfig
import mikufan.cx.vtool.core.n2vex.config.Preference
import mikufan.cx.vtool.module.model.niconico.NicoListItem
import mikufan.cx.vtool.module.model.niconico.NicoListSortPreference
import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.module.model.vocadb.PvService
import mikufan.cx.vtool.module.model.vocadb.VocaDBSongListItem
import mikufan.cx.vtool.service.api.NicoListFetcher
import mikufan.cx.vtool.service.api.VocaDbPvMapper

class MainExporter(
  private val ioConfig: IOConfig,
  preference: Preference,
  private val listFetcher: NicoListFetcher,
  private val pvMapper: VocaDbPvMapper,
) : Runnable {

  private val sortPreference = NicoListSortPreference(preference.sortKey, preference.sortOrder)

  override fun run() {
    val songsItr = listFetcher.readAllSongsFromList(ioConfig.nicoListId, sortPreference)
    val resultList = mapNicoListToVocaDbList(songsItr)
    val notFoundList = resultList.filter { it.vocaDbId == null }.map { it.nicoItem }
    val foundList = resultList.filter { it.vocaDbId != null }.map { VocaDBSongListItem(it.vocaDbId!!, it.nicoItem.note) }
    log.info { "Found ${foundList.size} songs, not found ${notFoundList.size} songs" }
  }

  private fun mapNicoListToVocaDbList(songsItr: Iterator<NicoListItem>): List<MappedResult> = runBlocking(Dispatchers.Default) {
    songsItr.asSequence()
      .map { song -> song to async { pvMapper.tryFindRecord(PV(song.id, PvService.NicoNicoDouga, song.title)) } }
      .toList()
      .map { (song, deferred) ->
        val vocadbId = deferred.await()
        MappedResult(song, vocadbId)
      }
  }

}

private data class MappedResult(
  val nicoItem: NicoListItem,
  val vocaDbId: Long?,
)

private val log = KInlineLogging.logger()