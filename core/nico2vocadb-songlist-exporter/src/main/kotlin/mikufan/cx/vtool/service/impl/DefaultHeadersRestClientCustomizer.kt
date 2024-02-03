package mikufan.cx.vtool.service.impl

import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient

class DefaultHeadersRestClientCustomizer(
  private val defaultHeaders: MultiValueMap<String, String>
) : RestClientCustomizer {
  override fun customize(restClientBuilder: RestClient.Builder) {
    restClientBuilder.defaultHeaders {
      it.addAll(defaultHeaders)
    }
  }
}