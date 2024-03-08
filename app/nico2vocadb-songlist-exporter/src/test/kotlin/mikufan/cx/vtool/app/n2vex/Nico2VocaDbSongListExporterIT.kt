package mikufan.cx.vtool.app.n2vex

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import mikufan.cx.vtool.core.n2vex.MainExporterWithLocalWrite
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@Ignored
class Nico2VocaDbSongListExporterIT(
  private val mainExporterWithLocalWrite: MainExporterWithLocalWrite,
) : ShouldSpec({
  context("main exporter") {
    should("run") {
      shouldNotThrow<Exception> {
        mainExporterWithLocalWrite.run()
      }
    }
  }
}) {
  override fun extensions() = listOf(SpringExtension)
}
