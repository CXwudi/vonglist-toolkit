package mikufan.cx.vtool.app.n2vex.config

import jakarta.annotation.PreDestroy
import mikufan.cx.vtool.util.apachehttpclient.api.CookieStorePersistor
import mikufan.cx.vtool.util.apachehttpclient.impl.NetscapeTxtCookieStorePersistor
import org.apache.hc.client5.http.cookie.CookieStore
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class NicoNicoHttpClientConfig(
  httpConfigProperties: HttpConfigProperties,
) {

  private var cookieStorePersistor: CookieStorePersistor? =
    httpConfigProperties.cookieJarTxt?.let { NetscapeTxtCookieStorePersistor(it) }
  private var cookieStore: CookieStore? = cookieStorePersistor?.load()

  @Bean
  fun niconicoHttpClient(): CloseableHttpClient = HttpClients.custom().apply {
    cookieStore?.let { setDefaultCookieStore(it) }
  }
    .useSystemProperties()
    .build()


  @PreDestroy
  fun persist() {
    cookieStorePersistor?.persist(cookieStore!!)
  }
}
