package mikufan.cx.vtool.app.n2vex.config

import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class UserAgentRestClientCustomizer(
  httpConfigProperties: HttpConfigProperties
) : RestClientCustomizer {

  private val userAgent = httpConfigProperties.userAgent

  override fun customize(restClientBuilder: RestClient.Builder) {
    restClientBuilder.defaultHeader(HttpHeaders.USER_AGENT, userAgent)
  }
}