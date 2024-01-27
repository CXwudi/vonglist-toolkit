package mikufan.cx.vtool.service.api

import mikufan.cx.vtool.module.model.niconico.NicoListItem
import mikufan.cx.vtool.module.model.niconico.NicoListSortPreference

fun interface NicoListFetcher {
  fun readAllSongsFromList(
    id: Long,
    sortPreference: NicoListSortPreference,
  ): NicoListItemIterator
}

interface NicoListItemIterator : Iterator<NicoListItem>