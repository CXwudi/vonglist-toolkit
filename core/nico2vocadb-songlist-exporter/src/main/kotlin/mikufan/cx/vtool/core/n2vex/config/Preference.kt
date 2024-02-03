package mikufan.cx.vtool.core.n2vex.config

import mikufan.cx.vtool.module.model.niconico.NicoListSortKey
import mikufan.cx.vtool.module.model.niconico.NicoListSortOrder

interface Preference {
  val sortKey: NicoListSortKey?
  val sortOrder: NicoListSortOrder?
  val usePrivateApi: Boolean
  val useVocadbCsvFormat: Boolean
}
