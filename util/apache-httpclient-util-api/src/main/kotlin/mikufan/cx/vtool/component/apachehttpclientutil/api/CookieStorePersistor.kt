package mikufan.cx.vtool.component.apachehttpclientutil.api

import org.apache.hc.client5.http.cookie.CookieStore

interface CookieStorePersistor {

  fun persist(cookieJar: CookieStore)

  fun load(): CookieStore
}