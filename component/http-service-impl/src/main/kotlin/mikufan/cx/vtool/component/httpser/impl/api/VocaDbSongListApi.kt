package mikufan.cx.vtool.component.httpser.impl.api

import com.fasterxml.jackson.databind.JsonNode
import jakarta.validation.constraints.NotNull
import mikufan.cx.vtool.shared.model.vocadb.VocaDbSongListItemSortOrder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

/**
 * Interface for interacting with the VocaDB API.
 * Provides methods for fetching and pushing song lists.
 */
@HttpExchange("/api")
@Validated
interface VocaDbSongListApi {

  /**
   * Fetches a song list for editing by its ID.
   * @param id The ID of the song list.
   * @return Either the song list Json as a string, or the failed HTML as a string.
   */
  @GetExchange("/songLists/{id}/for-edit")
  fun getForEditById(
    @PathVariable @NotNull id: Long
  ): String

  /**
   * Fetches all songs from a song list by its ID.
   * @param id The ID of the song list.
   * @return song list Json, the json will contain empty array if no songs or no song list found.
   */
  @GetExchange("/songLists/{id}/songs")
  fun getSongList(
    @PathVariable @NotNull id: Long,
    /**
     * The start index of the songs to fetch.
     */
    @RequestParam(required = false) start: Int? = null,
    @RequestParam(required = false) maxResults: Int? = null,
    @RequestParam(required = false) getTotalCount: Boolean = true,
    @RequestParam(required = false) sort: VocaDbSongListItemSortOrder? = null,
  ): JsonNode

  /**
   * Pushes a song list to VocaDB.
   * @param json The song list as a JsonNode.
   * @return Either the ID of the pushed song list as a string, or the failed HTML as a string.
   */
  @PostExchange("/songLists")
  fun pushSongList(
    @RequestBody @NotNull json: JsonNode
  ): String
}