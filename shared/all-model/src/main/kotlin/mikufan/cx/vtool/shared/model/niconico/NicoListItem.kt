package mikufan.cx.vtool.shared.model.niconico

import java.util.*

data class NicoListItem(
  val id: String,
  /**
   * Optional but better for debugging
   */
  val title: String,
  val note: String,
) {
  override fun hashCode(): Int = Objects.hash(id, note)
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is NicoListItem) return false
    return id == other.id && note == other.note
  }
}
