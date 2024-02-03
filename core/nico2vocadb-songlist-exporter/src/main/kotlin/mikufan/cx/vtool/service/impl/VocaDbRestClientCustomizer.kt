package mikufan.cx.vtool.service.impl

import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.web.client.RestClient

class VocaDbRestClientCustomizer : RestClientCustomizer {
  override fun customize(restClientBuilder: RestClient.Builder) {
    restClientBuilder.baseUrl("https://vocadb.net")
  }
}