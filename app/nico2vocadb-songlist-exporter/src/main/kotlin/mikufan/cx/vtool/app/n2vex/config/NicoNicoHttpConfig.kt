package mikufan.cx.vtool.app.n2vex.config

import mikufan.cx.vtool.component.httpser.impl.api.NicoListApi
import mikufan.cx.vtool.component.httpser.impl.customizer.NvApiRestClientCustomizer
import mikufan.cx.vtool.shared.model.niconico.NicoListSortKey
import mikufan.cx.vtool.shared.model.niconico.NicoListSortOrder
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder
import org.apache.hc.core5.http.HttpHeaders
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration(proxyBeanMethods = false)
class NicoNicoHttpConfig(
  httpConfigProperties: HttpConfigProperties,
  private val restClientBuilder: RestClient.Builder
) {

  private val niconicoUserSession: String? = httpConfigProperties.niconicoUserSessionCookieValue

  @Bean
  fun niconicoHttpServiceProxyFactory(
    niconicoHttpClientBuilder: HttpClientBuilder,
  ): HttpServiceProxyFactory {
    restClientBuilder.requestFactory(HttpComponentsClientHttpRequestFactory(niconicoHttpClientBuilder.build()))
    NvApiRestClientCustomizer(enableWriteOperations = false).customize(restClientBuilder)
    if (!niconicoUserSession.isNullOrBlank()) {
      restClientBuilder.defaultHeader(HttpHeaders.COOKIE, "user_session=$niconicoUserSession")
    }
    val niconicoConversionService = DefaultFormattingConversionService().apply {
      addConverter(NicoListSortKeyConverter())
      addConverter(NicoListSortOrderConverter())
    }
    return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClientBuilder.build()))
      .conversionService(niconicoConversionService)
      .build()
  }

  @Bean
  fun mylistApi(
    @Qualifier("niconicoHttpServiceProxyFactory") httpServiceProxyFactory: HttpServiceProxyFactory,
  ) = httpServiceProxyFactory.createClient<NicoListApi>()

}

private class NicoListSortKeyConverter : Converter<NicoListSortKey, String> {
  override fun convert(source: NicoListSortKey): String = source.value
}

private class NicoListSortOrderConverter : Converter<NicoListSortOrder, String> {
  override fun convert(source: NicoListSortOrder): String = source.value
}
