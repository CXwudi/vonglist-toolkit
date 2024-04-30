package mikufan.cx.vtool.component.httpser.impl

import com.fasterxml.jackson.databind.node.ArrayNode
import mikufan.cx.vtool.component.httpser.api.VocaDbSongListItemIterator
import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongListApi
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem
import mikufan.cx.vtool.shared.model.vocadb.VocaDbSongListItemSortOrder

class LazyApiCallVocaDbSongListItemItr(
  private val vocaDbSongListApi: VocaDbSongListApi,
  private val id: Long,
  private val sortOrder: VocaDbSongListItemSortOrder? = null,
) : VocaDbSongListItemIterator {

  companion object {
    private const val DEFAULT_PAGE_SIZE = 100
  }
  val buffer = ArrayDeque<VocaDBSongListItem>(DEFAULT_PAGE_SIZE)
  var nextStartIdx = 0
  var total: Int? = null

  override fun hasNext(): Boolean {
    val hasNext = total == null || nextStartIdx < total!!
    if (hasNext && buffer.isEmpty()) {
      doCallApi()
    }
    return buffer.isNotEmpty()
  }

  override fun next(): VocaDBSongListItem = buffer.removeFirst()

  private fun doCallApi() {
    val json = vocaDbSongListApi.getSongList(
      id = id,
      start = nextStartIdx,
      maxResults = DEFAULT_PAGE_SIZE,
      sort = sortOrder
    )
    if (total == null) {
      total = json["totalCount"].asInt()
    }
    val items = json["items"] as ArrayNode
    items.forEach {
      val note = it["notes"].asText()
      val id = it["song"]["id"].asLong()
      buffer += VocaDBSongListItem(id, note)
      nextStartIdx++
    }
  }
}