package mikufan.cx.vtool.module.model.niconico

data class NicoListItem(
  val id: String,
  /**
   * Optional but better for debugging
   */
  val title: String,
  val note: String,
)
