package mikufan.cx.vtool.component.httpser.impl.customizer

import org.springframework.boot.web.client.RestClientCustomizer
import org.springframework.util.MultiValueMap
import org.springframework.util.MultiValueMapAdapter
import org.springframework.web.client.RestClient

class DefaultHeadersRestClientCustomizer(
  private val defaultHeaders: Map<String, List<String>>
) : RestClientCustomizer {
  override fun customize(restClientBuilder: RestClient.Builder) {
    restClientBuilder.defaultHeaders {
      val multiValueMap = if (defaultHeaders is MultiValueMap<*, *>) {
        defaultHeaders as MultiValueMap<String, String>
      } else {
        MultiValueMapAdapter(defaultHeaders)
      }
      it.addAll(multiValueMap)
    }
  }
}