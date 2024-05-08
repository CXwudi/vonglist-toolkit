package mikufan.cx.vtool.app.vsle.config

import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongListApi
import mikufan.cx.vtool.component.httpser.impl.customizer.UserAgentRestClientCustomizer
import mikufan.cx.vtool.component.httpser.impl.customizer.VocaDbRestClientCustomizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration(proxyBeanMethods = false)
class HttpConfig {

  @Bean
  fun httpServiceProxyFactory(
    @Value("\${system.http.user-agent}") userAgent: String,
    restClientBuilder: RestClient.Builder,
  ): HttpServiceProxyFactory {
    UserAgentRestClientCustomizer(userAgent).customize(restClientBuilder)
    VocaDbRestClientCustomizer().customize(restClientBuilder)
    return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClientBuilder.build()))
      .build()
  }

  @Bean
  fun vocadbSongListApi(
    httpServiceProxyFactory: HttpServiceProxyFactory,
  ) = httpServiceProxyFactory.createClient<VocaDbSongListApi>()

}