package mikufan.cx.vtool.app.vsli

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import mikufan.cx.vtool.core.vsli.MainImporterWithLocalRead
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(properties = [
//  "system.http.cookie-jar-txt=vocadb.net_cookies.txt"
])
@ActiveProfiles("test")
class VocaDbSongListImporterIT(
  private val mainImporterWithLocalRead: MainImporterWithLocalRead,
) : ShouldSpec({

  xcontext("main importer") {
    should("run") {
      shouldNotThrow<Exception> {
        mainImporterWithLocalRead.run()
      }
    }
  }
}) {
  override fun extensions() = listOf(SpringExtension)
}
