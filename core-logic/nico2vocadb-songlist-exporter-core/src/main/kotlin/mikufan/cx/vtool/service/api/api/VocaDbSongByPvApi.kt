package mikufan.cx.vtool.service.api.api

import com.fasterxml.jackson.databind.JsonNode
import mikufan.cx.vtool.module.model.vocadb.PvService
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange("/api")
interface VocaDbSongByPvApi {

  @GetExchange("/songs/byPv")
  fun getSongByPv(
    @RequestParam pvId: String,
    @RequestParam pvService: PvService,
  ): JsonNode?

}