package mikufan.cx.vtool.core.vsle

import mikufan.cx.vtool.component.httpser.api.VocaDbSongListFetcher
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem
import mikufan.cx.vtool.shared.model.vocadb.VocaDbSongListItemSortOrder

class MainExporter(
  private val vocaDbSongListFetcher: VocaDbSongListFetcher
) {

  fun exportSongList(listId: Long, sortOrder: VocaDbSongListItemSortOrder? = null): List<VocaDBSongListItem> {
    val songListItr = vocaDbSongListFetcher.fetchSongList(listId, sortOrder)
    return songListItr.asSequence().toList()
  }
}