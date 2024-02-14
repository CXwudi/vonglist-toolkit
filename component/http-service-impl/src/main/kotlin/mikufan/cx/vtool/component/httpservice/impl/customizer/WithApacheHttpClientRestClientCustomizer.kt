package mikufan.cx.vtool.component.httpservice.impl.customizer

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient

class WithApacheHttpClientRestClientCustomizer(
  private val httpClient: CloseableHttpClient,
) : RestClientCustomizer {

  override fun customize(restClientBuilder: RestClient.Builder) {
    val clientHttpRequestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
    restClientBuilder.requestFactory(clientHttpRequestFactory)
  }
}