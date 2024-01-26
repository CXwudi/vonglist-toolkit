package mikufan.cx.vtool.module.model.niconico

import com.fasterxml.jackson.databind.JsonNode

data class NvApiResponse(
  val meta: NvApiResponseMeta,
  val data: JsonNode,
)

data class NvApiResponseMeta(
  val status: Int,
  val errorCode: String?, // this can probably be an enum, but I don't know what are the possible values
)
