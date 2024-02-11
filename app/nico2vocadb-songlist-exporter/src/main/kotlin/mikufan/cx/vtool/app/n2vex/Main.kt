package mikufan.cx.vtool.app.n2vex

import mikufan.cx.vtool.core.n2vex.MainExporterWithLocalWrite
import org.springframework.beans.factory.getBean
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
  runApplication<Nico2VocaDbSongListExporter>(*args).run {
    getBean<MainExporterWithLocalWrite>().run()
  }
}