package mikufan.cx.vtool.component.apachehttpclientutil.impl

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.apachehttpclientutil.api.CookieStorePersistor
import org.apache.hc.client5.http.cookie.BasicCookieStore
import org.apache.hc.client5.http.cookie.Cookie
import org.apache.hc.client5.http.cookie.CookieStore
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie
import java.nio.file.Path
import java.time.Instant
import kotlin.io.path.bufferedReader
import kotlin.io.path.bufferedWriter

class NetscapeTxtCookieStorePersistor(
  private val txtFile: Path
) : CookieStorePersistor {
  override fun persist(cookieJar: CookieStore) {
    txtFile.bufferedWriter().use { writer ->
      cookieJar.cookies.forEach { cookie ->
        writer.write(
          "${cookie.domain}\tTRUE\t${cookie.path}\t${cookie.isSecure}\t${cookie.expiryInstant.epochSecond}\t${cookie.name}\t${cookie.value}\n"
        )
      }
    }
  }

  override fun load(): CookieStore {
    val cookieStore = BasicCookieStore()
    txtFile.bufferedReader().use { reader ->
      reader.lineSequence().forEach { line ->
        if (line.startsWith("#")) return@forEach
        val parts = line.split("\t")
        if (parts.size == 7) {
          val cookie = BasicClientCookie(parts[5], parts[6]).apply {
            domain = parts[0]
            path = parts[2]
            isSecure = parts[3].toBoolean()
            setExpiryDate(Instant.ofEpochSecond(parts[4].toLong()))
            // this is needed, see https://www.baeldung.com/httpclient-cookies#1-httpclient-after-43
            setAttribute(Cookie.DOMAIN_ATTR, true.toString())
          }
          cookieStore.addCookie(cookie)
        } else {
          log.warn { "Invalid cookie line: $line" }
        }
      }
    }
    return cookieStore
  }
}

private val log = KInlineLogging.logger()