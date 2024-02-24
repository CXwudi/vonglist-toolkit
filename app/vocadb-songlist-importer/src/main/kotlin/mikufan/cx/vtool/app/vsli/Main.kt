package mikufan.cx.vtool.app.vsli

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
  runApplication<VocaDbSongListImporter>(*args).use {

  }
}


@SpringBootApplication
@ConfigurationPropertiesScan
class VocaDbSongListImporter