package mikufan.cx.vtool.service.api.api

import jakarta.validation.constraints.Max
import mikufan.cx.vtool.module.model.niconico.NicoListSortKey
import mikufan.cx.vtool.module.model.niconico.NicoListSortOrder
import mikufan.cx.vtool.module.model.niconico.NvApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange
interface NicoListApi {

  @GetExchange("/v2/mylists/{mylistId}")
  fun getPublicList(
    @PathVariable mylistId: Long,
    @RequestParam sortKey: NicoListSortKey?,
    @RequestParam sortOrder: NicoListSortOrder?,
    @RequestParam page: Int?,
    @RequestParam @Max(100) pageSize: Int?,
  ): ResponseEntity<NvApiResponse>

  @GetExchange("/v1/users/me/mylists/{mylistId}")
  fun getPrivateList(
    @PathVariable mylistId: Long,
    @RequestParam sortKey: NicoListSortKey?,
    @RequestParam sortOrder: NicoListSortOrder?,
    @RequestParam page: Int?,
    @RequestParam @Max(100) pageSize: Int?,
  ): ResponseEntity<NvApiResponse>

}