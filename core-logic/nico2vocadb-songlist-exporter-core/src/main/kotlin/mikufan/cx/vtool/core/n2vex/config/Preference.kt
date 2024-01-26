package mikufan.cx.vtool.core.n2vex.config

import mikufan.cx.vtool.module.model.niconico.NicoListSortKey
import mikufan.cx.vtool.module.model.niconico.NicoListSortOrder

data class Preference(
  var sortKey: NicoListSortKey?,
  var sortOrder: NicoListSortOrder?,
  var usePrivateApi: Boolean,
  var useVocaDbCsvFormat: Boolean,
)
