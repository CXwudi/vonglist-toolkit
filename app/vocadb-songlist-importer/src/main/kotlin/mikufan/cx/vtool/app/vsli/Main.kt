package mikufan.cx.vtool.app.vsli

import mikufan.cx.vtool.core.vsli.MainImporterWithLocalRead
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
  runApplication<VocaDbSongListImporter>(*args).use {
    it.getBean<MainImporterWithLocalRead>().run()
  }
}


@SpringBootApplication
@ConfigurationPropertiesScan
class VocaDbSongListImporter