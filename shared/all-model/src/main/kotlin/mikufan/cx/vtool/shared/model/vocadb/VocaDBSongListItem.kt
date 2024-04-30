package mikufan.cx.vtool.shared.model.vocadb

data class VocaDBSongListItem(
  val id: Long,
  val note: String,
)

enum class VocaDbSongListItemSortOrder {
  /**
   * None doesn't mean no sort order.
   * It means the sort option called 'Nothing',
   * which is kind of a wired order.
   *
   * Not sure what is the purpose of this order, but it is a valid order in VocaDB
   */
  None,
  Name, AdditionDate, PublishDate, FavoritedTimes, RatingScore, TagUsageCount, SongType;
}