package mikufan.cx.vtool.app.vsli.config

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import mikufan.cx.vtool.component.httpser.api.VocaDbSongListPusher
import mikufan.cx.vtool.component.httpser.impl.VocaDbSongListPusherImpl
import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongListApi
import mikufan.cx.vtool.component.io.api.ItemReader
import mikufan.cx.vtool.component.io.impl.FromCsvItemReader
import mikufan.cx.vtool.component.io.impl.VocaDbCompatibleSongListCsvReader
import mikufan.cx.vtool.core.vsli.MainImporter
import mikufan.cx.vtool.core.vsli.MainImporterWithLocalRead
import mikufan.cx.vtool.core.vsli.component.JsonHandles
import mikufan.cx.vtool.core.vsli.config.IOConfig
import mikufan.cx.vtool.core.vsli.config.Preference
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

@Configuration(proxyBeanMethods = false)
class CoreComponentConfig {

  @Bean
  fun loomDispatcher(): CoroutineDispatcher = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()

  @Bean
  fun jsonHandles(objectMapper: ObjectMapper, loomDispatcher: CoroutineDispatcher): JsonHandles = JsonHandles(objectMapper, loomDispatcher)

  @Bean
  fun vocaDbSongListPusher(vocaDbSongListApi: VocaDbSongListApi, objectMapper: ObjectMapper) =
    VocaDbSongListPusherImpl(vocaDbSongListApi, objectMapper)

  @Bean
  fun mainImporter(jsonHandles: JsonHandles, vocaDbSongListPusher: VocaDbSongListPusher) =
    MainImporter(jsonHandles, vocaDbSongListPusher)

  @Bean
  fun csvReader(preference: Preference, ioConfig: IOConfig): ItemReader<VocaDBSongListItem> {
    return if (preference.useVocadbCsvFormat) {
      VocaDbCompatibleSongListCsvReader(ioConfig.inputCsv)
    } else {
      FromCsvItemReader<VocaDBSongListItem>(ioConfig.inputCsv)
    }
  }

  @Bean
  fun mainImporterWithLocalRead(
    ioConfig: IOConfig,
    csvReader: ItemReader<VocaDBSongListItem>,
    mainImporter: MainImporter,
  ) : MainImporterWithLocalRead {
    return MainImporterWithLocalRead(csvReader, ioConfig.vocaDbListId, mainImporter)
  }
}