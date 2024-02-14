package mikufan.cx.vtool.component.httpservice.impl

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.httpservice.api.NicoListFetcher
import mikufan.cx.vtool.component.httpservice.api.NicoListItemIterator
import mikufan.cx.vtool.component.httpservice.impl.api.NicoListApi
import mikufan.cx.vtool.module.model.niconico.NicoListSortPreference

class NicoListFetcherImpl(
  private val nicoListApi: NicoListApi,
  private val usePrivateApi: Boolean,
) : NicoListFetcher {

  override fun readAllSongsFromList(
    id: Long,
    sortPreference: NicoListSortPreference,
  ): NicoListItemIterator {
    log.info { "Lazily reading all songs from list $id with sort preference $sortPreference" }
    return LazyApiCallNicoListItemItr(nicoListApi, id, sortPreference, usePrivateApi)
  }
}

private val log = KInlineLogging.logger()