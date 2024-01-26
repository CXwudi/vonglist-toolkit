package mikufan.cx.vtool.module.model.niconico

data class NicoListSortPreference(
  val sortKey: NicoListSortKey?,
  val sortOrder: NicoListSortOrder?,
)

enum class NicoListSortOrder(val value: String) {
  Ascending("asc"),
  Descending("desc");

  override fun toString(): String {
    return value
  }
}

enum class NicoListSortKey(val value: String) {
  AddedAt("addedAt"),
  Title("title"),
  MylistComment("mylistComment"),
  RegisteredAt("registeredAt"),
  ViewCount("viewCount"),
  LastComment("lastComment"),
  CommentCount("commentCount"),
  LikeCount("likeCount"),
  MylistCount("mylistCount"),
  Duration("duration");

  override fun toString(): String {
    return value
  }
}