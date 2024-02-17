package mikufan.cx.vtool.core.n2vex

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.httpser.api.NicoListFetcher
import mikufan.cx.vtool.component.httpser.api.PvMapper
import mikufan.cx.vtool.shared.model.niconico.NicoListItem
import mikufan.cx.vtool.shared.model.niconico.NicoListSortPreference
import mikufan.cx.vtool.shared.model.vocadb.PV
import mikufan.cx.vtool.shared.model.vocadb.PvService
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem

class MainExporter(
  private val listFetcher: NicoListFetcher,
  private val pvMapper: PvMapper,
) {

  fun exportToVocaDbList(nicoListId: Long, sortPreference: NicoListSortPreference): Nico2VocaDbMapResult {
    val songsItr = listFetcher.readAllSongsFromList(nicoListId, sortPreference)
    val resultList = mapNicoListToVocaDbList(songsItr)
    val notFoundList = resultList.filter { it.vocaDbId == null }.map { it.nicoItem }
    val foundList = resultList.filter { it.vocaDbId != null }.map { VocaDBSongListItem(it.vocaDbId!!, it.nicoItem.note) }
    log.info { "Found ${foundList.size} songs, not found ${notFoundList.size} songs. Now recording all results" }
    return Nico2VocaDbMapResult(notFoundList, foundList)
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

internal data class MappedResult(
  val nicoItem: NicoListItem,
  val vocaDbId: Long?,
)

data class Nico2VocaDbMapResult(
  val notFoundList: List<NicoListItem>,
  val foundList: List<VocaDBSongListItem>,
)

private val log = KInlineLogging.logger()