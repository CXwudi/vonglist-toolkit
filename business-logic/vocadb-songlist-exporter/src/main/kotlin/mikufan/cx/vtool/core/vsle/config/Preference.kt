package mikufan.cx.vtool.core.vsle.config

import mikufan.cx.vtool.shared.model.vocadb.VocaDbSongListItemSortOrder

interface Preference {
  val sortOrder: VocaDbSongListItemSortOrder?
  val useVocadbCsvFormat: Boolean
}