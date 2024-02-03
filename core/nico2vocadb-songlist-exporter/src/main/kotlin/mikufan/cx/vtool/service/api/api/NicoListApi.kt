package mikufan.cx.vtool.service.api.api

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import mikufan.cx.vtool.module.model.niconico.NvApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange
@Validated
interface NicoListApi {

  @GetExchange("/v2/mylists/{mylistId}")
  fun getPublicList(
    @PathVariable mylistId: Long,
    @RequestParam(required = false) sortKey: String?,
    @RequestParam(required = false) sortOrder: String?,
    @RequestParam(required = false) @Min(1) page: Int?,
    @RequestParam(required = false) @Min(1) @Max(100) pageSize: Int?,
  ): ResponseEntity<NvApiResponse>

  @GetExchange("/v1/users/me/mylists/{mylistId}")
  fun getPrivateList(
    @PathVariable mylistId: Long,
    @RequestParam(required = false) sortKey: String?,
    @RequestParam(required = false) sortOrder: String?,
    @RequestParam(required = false) @Min(1) page: Int?,
    @RequestParam(required = false) @Min(1) @Max(100) pageSize: Int?,
  ): ResponseEntity<NvApiResponse>

}