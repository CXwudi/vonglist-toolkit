package mikufan.cx.vtool.core.vsle

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.httpser.api.VocaDbSongListFetcher
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem
import mikufan.cx.vtool.shared.model.vocadb.VocaDbSongListItemSortOrder

class MainExporter(
  private val vocaDbSongListFetcher: VocaDbSongListFetcher
) {

  fun exportSongList(listId: Long, sortOrder: VocaDbSongListItemSortOrder? = null): List<VocaDBSongListItem> {
    val songListItr = vocaDbSongListFetcher.fetchSongList(listId, sortOrder)
    val items = songListItr.asSequence().toList()
    log.info { "Exported ${items.size} items from song list with ID $listId " }
    return items
  }
}

private val log = KInlineLogging.logger()