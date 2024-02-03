package mikufan.cx.vtool.module.model.vocadb

import java.util.*


data class PV(
  val pvId: String,
  val pvService: PvService,
  /**
   * optional, only for debug purpose
   */
  val pvTitle: String = "",
) {

  override fun hashCode(): Int = Objects.hash(pvId, pvService)
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is PV) return false
    return pvId == other.pvId && pvService == other.pvService
  }
}
