package mikufan.cx.vtool.module.model

import mikufan.cx.vtool.module.model.vocadb.VocaDbCacheKeys

object AllCacheNames {
  operator fun invoke() = listOf(
    VocaDbCacheKeys.SONG_BY_PV,
  )
}