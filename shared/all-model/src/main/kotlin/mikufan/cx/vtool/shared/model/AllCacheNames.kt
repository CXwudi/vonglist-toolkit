package mikufan.cx.vtool.shared.model

import mikufan.cx.vtool.shared.model.vocadb.VocaDbCacheKeys

object AllCacheNames {
  operator fun invoke() = listOf(
    VocaDbCacheKeys.SONG_BY_PV,
  )
}