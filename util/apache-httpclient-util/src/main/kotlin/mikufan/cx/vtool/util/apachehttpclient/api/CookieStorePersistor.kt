package mikufan.cx.vtool.util.apachehttpclient.api

import org.apache.hc.client5.http.cookie.CookieStore

/**
 * Cookie store persistor
 *
 * Implementation Note:
 * If another implementation for another format like JSON is needed, consider creating another Gradle module for it
 *
 */
interface CookieStorePersistor {

  fun persist(cookieJar: CookieStore)

  fun load(): CookieStore
}