package mikufan.cx.vtool.core.vsli

import mikufan.cx.vtool.component.httpser.api.VocaDbSongListPusher
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem

class MainImporter(
  private val vocaDbSongListPusher: VocaDbSongListPusher,
) {

  fun importToVocaDb(songItems: List<VocaDBSongListItem>, toListId: Long) {

  }
}