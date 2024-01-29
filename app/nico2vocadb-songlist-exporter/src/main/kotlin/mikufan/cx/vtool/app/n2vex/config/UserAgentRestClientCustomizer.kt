package mikufan.cx.vtool.app.n2vex.config

import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class UserAgentRestClientCustomizer(
  systemConfig: SystemConfig
) : RestClientCustomizer {

  private val userAgent = systemConfig.userAgent

  override fun customize(restClientBuilder: RestClient.Builder) {
    restClientBuilder.defaultHeader(HttpHeaders.USER_AGENT, userAgent)
  }
}