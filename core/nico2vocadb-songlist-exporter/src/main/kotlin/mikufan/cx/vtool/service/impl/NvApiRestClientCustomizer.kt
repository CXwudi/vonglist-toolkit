package mikufan.cx.vtool.service.impl

import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient

class NvApiRestClientCustomizer(
  private val enableWriteOperations: Boolean = true,
) : RestClientCustomizer {
  override fun customize(restClientBuilder: RestClient.Builder) {
    restClientBuilder.baseUrl("https://nvapi.nicovideo.jp")
    restClientBuilder.defaultHeaders {
      it["X-Frontend-Id"] = "6"
      it["X-Frontend-Version"] = "0"
      it[HttpHeaders.ACCEPT] = "*/*"
      if (enableWriteOperations) {
        it["X-Request-With"] = "https://www.nicovideo.jp"
      }
    }
  }

}