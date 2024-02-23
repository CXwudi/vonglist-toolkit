package mikufan.cx.vtool.component.httpser.impl.api

import com.fasterxml.jackson.databind.JsonNode
import mikufan.cx.vtool.shared.model.vocadb.PvService
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

/**
 * Interface for interacting with the VocaDB API.
 * Provides methods for fetching songs by PV.
 */
@HttpExchange("/api")
interface VocaDbSongByPvApi {

  /**
   * Fetches a song by its PV.
   * @param pvId The ID of the PV.
   * @param pvService The service of the PV.
   * @return The song as a JsonNode, or null if not found.
   */
  @GetExchange("/songs/byPv")
  fun getSongByPv(
    @RequestParam pvId: String,
    @RequestParam pvService: PvService,
  ): JsonNode?

}