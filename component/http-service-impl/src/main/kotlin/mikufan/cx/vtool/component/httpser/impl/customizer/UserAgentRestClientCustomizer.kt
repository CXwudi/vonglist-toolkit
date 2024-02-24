package mikufan.cx.vtool.component.httpser.impl.customizer

import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient

class UserAgentRestClientCustomizer(
  private val userAgent: String
) : RestClientCustomizer {

  override fun customize(restClientBuilder: RestClient.Builder) {
    restClientBuilder.defaultHeader(HttpHeaders.USER_AGENT, userAgent)
  }
}