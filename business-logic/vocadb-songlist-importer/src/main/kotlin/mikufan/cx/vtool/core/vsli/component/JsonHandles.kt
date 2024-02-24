package mikufan.cx.vtool.core.vsli.component

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem

/**
 * Provides methods to handle JSON operations related to VocaDB song list items.
 *
 * @property objectMapper An instance of ObjectMapper to handle JSON operations.
 */
class JsonHandles(
  private val objectMapper: ObjectMapper,
) {

  fun createNewListJson(): JsonNode {
    val json = ObjectNode(objectMapper.nodeFactory)
    json.put("name", "New List")
    return json
  }

  fun setSongListToJson(json: JsonNode, items: List<VocaDBSongListItem>) {
    val mappedJsonNodeList = runBlocking(Dispatchers.Default) {
      items.mapIndexed { index, item -> async { mapOne(index, item) } }
        .map { it.await() }
    }
    (json as ObjectNode).putArray("songLinks").apply {
      addAll(mappedJsonNodeList)
    }
  }

  private fun mapOne(index: Int, item: VocaDBSongListItem): JsonNode {
    val realOrderInt = index + 1
    val json = ObjectNode(objectMapper.nodeFactory).apply {
      put("notes", item.note)
      put("order", realOrderInt)
      putObject("song").apply {
        put("id", item.id)
      }
    }
    return json
  }
}