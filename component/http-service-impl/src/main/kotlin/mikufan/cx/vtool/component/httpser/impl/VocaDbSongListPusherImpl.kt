package mikufan.cx.vtool.component.httpser.impl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.httpser.api.VocaDbSongListPusher
import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongListApi
import mikufan.cx.vtool.shared.model.util.VocaloidException


/**
 * Implementation of [VocaDbSongListPusher].
 * Uses [VocaDbSongListApi] and [ObjectMapper] for its operations.
 */
class VocaDbSongListPusherImpl(
  private val vocaDbSongListApi: VocaDbSongListApi,
  private val objectMapper: ObjectMapper
) : VocaDbSongListPusher {

  /**
   * Fetches a song list for editing by its ID.
   * If the fetched data is HTML, log an error and throw.
   * @param id The ID of the song list.
   * @return The song list as a JsonNode.
   */
  override fun getForEditById(id: Long): JsonNode {
    val raw = vocaDbSongListApi.getForEditById(id)
    return if (isHtml(raw)) {
      log.error { "VocaDB API returned HTML instead of JSON, indicating the id is wrong" }
      throw VocaloidException("VocaDB returned HTML instead of JSON: \n$raw")
    } else {
      objectMapper.readTree(raw)
    }
  }

  /**
   * Pushes a song list to VocaDB.
   * If the response is HTML, log an error and throw.
   * @param json The song list as a JsonNode.
   * @return The ID of the pushed song list.
   */
  override fun pushSongList(json: JsonNode): Long {
    val raw = vocaDbSongListApi.pushSongList(json)
    return if (isHtml(raw)) {
      log.error { "VocaDB API returned HTML instead of JSON, indicating the json is wrong" }
      throw VocaloidException("VocaDB returned HTML instead of JSON: \n$raw")
    } else {
      raw.toLong()
    }
  }

  /**
   * Checks if a string is HTML by checking if it starts with "<!DOCTYPE html>".
   * @param raw The string to check.
   * @return True if the string is HTML, false otherwise.
   */
  private fun isHtml(raw: String) = raw.startsWith("<!DOCTYPE html>")
}

private val log = KInlineLogging.logger()