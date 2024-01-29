package mikufan.cx.vtool.app.n2vex.config

import mikufan.cx.vtool.service.api.api.VocaDbSongByPvApi
import mikufan.cx.vtool.service.impl.VocaDbRestClientCustomizer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration(proxyBeanMethods = false)
class VocaDbHttpConfig(
  private val restClientBuilder: RestClient.Builder,
) {

  @Bean
  fun vocadbHttpServiceProxyFactory(): HttpServiceProxyFactory {
    VocaDbRestClientCustomizer().customize(restClientBuilder)
    return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClientBuilder.build()))
      .build()
  }

  @Bean
  fun vocadbSongByPvApi(
    @Qualifier("vocadbHttpServiceProxyFactory") httpServiceProxyFactory: HttpServiceProxyFactory,
  ) = httpServiceProxyFactory.createClient<VocaDbSongByPvApi>()

}