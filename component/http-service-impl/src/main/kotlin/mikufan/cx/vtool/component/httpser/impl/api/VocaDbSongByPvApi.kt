package mikufan.cx.vtool.component.httpser.impl.api

import com.fasterxml.jackson.databind.JsonNode
import mikufan.cx.vtool.shared.model.vocadb.PvService
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