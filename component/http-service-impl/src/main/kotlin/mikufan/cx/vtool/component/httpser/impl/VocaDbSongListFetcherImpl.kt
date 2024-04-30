package mikufan.cx.vtool.component.httpser.impl

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.httpser.api.VocaDbSongListFetcher
import mikufan.cx.vtool.component.httpser.api.VocaDbSongListItemIterator
import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongListApi
import mikufan.cx.vtool.shared.model.vocadb.VocaDbSongListItemSortOrder

class VocaDbSongListFetcherImpl(
  private val vocaDbSongListApi: VocaDbSongListApi,
) : VocaDbSongListFetcher {

  override fun fetchSongList(
    listId: Long,
    sortOrder: VocaDbSongListItemSortOrder?,
  ): VocaDbSongListItemIterator {
    log.info { "Lazily fetching all songs from list $listId with sort order $sortOrder" }
    return LazyApiCallVocaDbSongListItemItr(vocaDbSongListApi, listId, sortOrder)
  }
}

private val log = KInlineLogging.logger()