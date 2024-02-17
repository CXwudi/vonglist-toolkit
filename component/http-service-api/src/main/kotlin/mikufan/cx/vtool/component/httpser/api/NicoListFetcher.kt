package mikufan.cx.vtool.component.httpser.api

import mikufan.cx.vtool.shared.model.niconico.NicoListItem
import mikufan.cx.vtool.shared.model.niconico.NicoListSortPreference

interface NicoListFetcher {
  fun readAllSongsFromList(
    id: Long,
    sortPreference: NicoListSortPreference,
  ): NicoListItemIterator
}

interface NicoListItemIterator : Iterator<NicoListItem>