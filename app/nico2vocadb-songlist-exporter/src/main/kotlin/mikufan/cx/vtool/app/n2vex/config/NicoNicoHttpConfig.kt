package mikufan.cx.vtool.app.n2vex.config

import jakarta.annotation.PreDestroy
import mikufan.cx.vtool.module.model.niconico.NicoListSortKey
import mikufan.cx.vtool.module.model.niconico.NicoListSortOrder
import mikufan.cx.vtool.service.api.api.NicoListApi
import mikufan.cx.vtool.service.api.http.CookieStorePersistor
import mikufan.cx.vtool.service.impl.DefaultHeadersRestClientCustomizer
import mikufan.cx.vtool.service.impl.NetscapeTxtCookieStorePersistor
import mikufan.cx.vtool.service.impl.NvApiRestClientCustomizer
import mikufan.cx.vtool.service.impl.WithApacheHttpClientRestClientCustomizer
import org.apache.hc.client5.http.cookie.CookieStore
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.HttpHeaders
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.util.MultiValueMapAdapter
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration(proxyBeanMethods = false)
class NicoNicoHttpConfig(
  systemConfig: SystemConfig,
  private val restClientBuilder: RestClient.Builder
) {

  private val niconicoUserSession: String? = systemConfig.niconicoUserSessionCookieValue

  private var cookieStorePersistor: CookieStorePersistor? = systemConfig.cookieJarTxt?.let { NetscapeTxtCookieStorePersistor(it) }
  private var cookieStore: CookieStore? = cookieStorePersistor?.load()

  @Bean
  fun niconicoHttpClient(): CloseableHttpClient = HttpClients.custom().apply {
    cookieStore?.let { setDefaultCookieStore(it) }
  }
    .useSystemProperties()
    .build()

  @Bean
  fun niconicoHttpServiceProxyFactory(
    niconicoHttpClient: CloseableHttpClient,
  ): HttpServiceProxyFactory {
    WithApacheHttpClientRestClientCustomizer(niconicoHttpClient).customize(restClientBuilder)
    NvApiRestClientCustomizer(enableWriteOperations = false).customize(restClientBuilder)
    if (!niconicoUserSession.isNullOrBlank()) {
      val headerWithUserSession = mapOf(
        HttpHeaders.COOKIE to listOf("user_session=$niconicoUserSession")
      )
      DefaultHeadersRestClientCustomizer(MultiValueMapAdapter(headerWithUserSession)).customize(restClientBuilder)
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

  @PreDestroy
  fun persist() {
    cookieStorePersistor?.persist(cookieStore!!)
  }
}

private class NicoListSortKeyConverter : Converter<NicoListSortKey, String> {
  override fun convert(source: NicoListSortKey): String = source.value
}

private class NicoListSortOrderConverter : Converter<NicoListSortOrder, String> {
  override fun convert(source: NicoListSortOrder): String = source.value
}
