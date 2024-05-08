package mikufan.cx.vtool.app.vsle

import mikufan.cx.vtool.core.vsle.MainExporterWithLocalWrite
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
  runApplication<VocaDbSongListExporter>(*args).use {
    it.getBean<MainExporterWithLocalWrite>().run()
  }
}

@SpringBootApplication
@ConfigurationPropertiesScan
class VocaDbSongListExporter