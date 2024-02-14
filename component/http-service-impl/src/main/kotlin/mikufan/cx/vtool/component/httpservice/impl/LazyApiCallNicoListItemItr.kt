package mikufan.cx.vtool.component.httpservice.impl

import mikufan.cx.vtool.component.httpservice.api.NicoListItemIterator
import mikufan.cx.vtool.component.httpservice.impl.api.NicoListApi
import mikufan.cx.vtool.module.model.niconico.NicoListItem
import mikufan.cx.vtool.module.model.niconico.NicoListSortPreference

class LazyApiCallNicoListItemItr(
  private val nicoListApi: NicoListApi,
  private val id: Long,
  private val sortPreference: NicoListSortPreference,
  private val usePrivateApi: Boolean
) : NicoListItemIterator {

  companion object {
    private const val DEFAULT_PAGE_SIZE = 100
  }
  val buffer = ArrayDeque<NicoListItem>(DEFAULT_PAGE_SIZE)
  var hasNext = true

  var page = 1

  override fun hasNext(): Boolean {
    if (hasNext && buffer.isEmpty()) {
      doCallApi()
    }
    return buffer.isNotEmpty()
  }

  override fun next(): NicoListItem = buffer.removeFirst()
  private fun doCallApi() {
    val response = if (usePrivateApi) {
      nicoListApi.getPrivateList(id, sortPreference.sortKey, sortPreference.sortOrder, page++, DEFAULT_PAGE_SIZE)
    } else {
      nicoListApi.getPublicList(id, sortPreference.sortKey, sortPreference.sortOrder, page++, DEFAULT_PAGE_SIZE)
    }

    val statusCode = response.statusCode
    when {
      statusCode.is2xxSuccessful -> {
        val json = response.body?.data?.get("mylist")
          ?: throw IllegalStateException("can not find mylist in response body even in 2xx status code")
        hasNext = json["hasNext"].asBoolean()
        val items = json["items"]
        require(items.isArray) { "items in response body is not an array" }
        for (item in items) {
          val id = item["watchId"].asText()
          val title = item["video"]["title"].asText()
          val note = item["description"].asText()
          buffer.add(NicoListItem(id, title, note))
        }
      }
      statusCode.isError -> {
        error("error status code $statusCode, error code = ${response.body?.meta?.errorCode}, " +
            "check to see if the list is available, " +
            "or if you have the correct user_session cookie value in either the config file or cookie jar txt file")
      }
      else -> {
        error("unknown status code $statusCode")
      }
    }
  }
}