package mikufan.cx.vtool.component.httpservice.api

import mikufan.cx.vtool.module.model.niconico.NicoListItem
import mikufan.cx.vtool.module.model.niconico.NicoListSortPreference

interface NicoListFetcher {
  fun readAllSongsFromList(
    id: Long,
    sortPreference: NicoListSortPreference,
  ): NicoListItemIterator
}

interface NicoListItemIterator : Iterator<NicoListItem>