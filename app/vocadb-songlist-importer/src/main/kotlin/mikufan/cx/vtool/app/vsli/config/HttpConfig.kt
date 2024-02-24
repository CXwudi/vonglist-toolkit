package mikufan.cx.vtool.app.vsli.config

import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongListApi
import mikufan.cx.vtool.component.httpser.impl.customizer.UserAgentRestClientCustomizer
import mikufan.cx.vtool.component.httpser.impl.customizer.VocaDbRestClientCustomizer
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration(proxyBeanMethods = false)
class HttpConfig {

  @Bean
  fun vocadbHttpServiceProxyFactory(
    httpConfigProperties: HttpConfigProperties,
    httpClientBuilder: HttpClientBuilder,
    restClientBuilder: RestClient.Builder,
  ): HttpServiceProxyFactory {
    restClientBuilder.requestFactory(HttpComponentsClientHttpRequestFactory(httpClientBuilder.build()))
    VocaDbRestClientCustomizer().customize(restClientBuilder)
    UserAgentRestClientCustomizer(httpConfigProperties.userAgent).customize(restClientBuilder)
    val cookieValue = httpConfigProperties.aspNetCoreCookieValue
    if (!cookieValue.isNullOrBlank()) {
      restClientBuilder.defaultHeader(HttpHeaders.COOKIE, ".AspNetCore.Cookies=$cookieValue")
    }
    return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClientBuilder.build()))
      .build()
  }

  @Bean
  fun vocadbSongListApi(
    httpServiceProxyFactory: HttpServiceProxyFactory,
  ) = httpServiceProxyFactory.createClient<VocaDbSongListApi>()

}