package mikufan.cx.vtool.util.apachehttpclient.api

import org.apache.hc.client5.http.cookie.CookieStore

interface CookieStorePersistor {

  fun persist(cookieJar: CookieStore)

  fun load(): CookieStore
}