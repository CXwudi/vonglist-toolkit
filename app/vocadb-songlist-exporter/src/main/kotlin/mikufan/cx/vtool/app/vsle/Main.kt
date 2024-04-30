package mikufan.cx.vtool.app.vsle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
  runApplication<VocaDbSongListExporter>(*args).use {
    // TODO
  }
}

@SpringBootApplication
@ConfigurationPropertiesScan
class VocaDbSongListExporter