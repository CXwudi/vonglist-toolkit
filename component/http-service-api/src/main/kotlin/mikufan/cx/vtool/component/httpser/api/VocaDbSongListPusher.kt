package mikufan.cx.vtool.component.httpser.api

import com.fasterxml.jackson.databind.JsonNode

/**
 * Interface of service for pushing song lists to VocaDB.
 *
 * First fetches a song list for editing by its ID, then pushes a song list to VocaDB.
 *
 * If pushing a new song list, then just call [pushSongList] without setting an ID.
 */
interface VocaDbSongListPusher {

  /**
   * Fetches a song list for editing by its ID.
   * @param id The ID of the song list.
   * @return The song list as a JsonNode.
   */
  fun getForEditById(id: Long): JsonNode

  /**
   * Pushes a song list to VocaDB.
   * @param json The song list as a JsonNode.
   * @return The ID of the pushed song list.
   */
  fun pushSongList(json: JsonNode): Long
}