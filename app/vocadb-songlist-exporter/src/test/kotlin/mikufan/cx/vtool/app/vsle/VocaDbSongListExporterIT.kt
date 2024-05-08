package mikufan.cx.vtool.app.vsle

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import mikufan.cx.vtool.core.vsle.MainExporterWithLocalWrite
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(properties = [
//  "system.http.cookie-jar-txt=vocadb.net_cookies.txt"
])
@ActiveProfiles("test")
@Ignored
class VocaDbSongListExporterIT(
  private val mainExporterWithLocalWrite: MainExporterWithLocalWrite,
) : ShouldSpec({

  context("main exporter") {
    should("run") {
      mainExporterWithLocalWrite.run() shouldBe Unit
    }
  }

}){
  override fun extensions() = listOf(SpringExtension)
}
