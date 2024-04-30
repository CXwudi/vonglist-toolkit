package mikufan.cx.vtool.component.httpser.api

import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem
import mikufan.cx.vtool.shared.model.vocadb.VocaDbSongListItemSortOrder

interface VocaDbSongListFetcher {
  fun fetchSongList(
    listId: Long,
    sortOrder: VocaDbSongListItemSortOrder? = null,
  ): VocaDbSongListItemIterator
}

interface VocaDbSongListItemIterator : Iterator<VocaDBSongListItem>