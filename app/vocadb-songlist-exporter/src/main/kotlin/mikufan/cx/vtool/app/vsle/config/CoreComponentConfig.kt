package mikufan.cx.vtool.app.vsle.config

import mikufan.cx.vtool.component.httpser.api.VocaDbSongListFetcher
import mikufan.cx.vtool.component.httpser.impl.VocaDbSongListFetcherImpl
import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongListApi
import mikufan.cx.vtool.component.io.api.ItemRecorder
import mikufan.cx.vtool.component.io.impl.ToCsvItemRecorder
import mikufan.cx.vtool.component.io.impl.VocaDbCompatibleSongListCsvProducer
import mikufan.cx.vtool.core.vsle.MainExporter
import mikufan.cx.vtool.core.vsle.MainExporterWithLocalWrite
import mikufan.cx.vtool.core.vsle.config.IOConfig
import mikufan.cx.vtool.core.vsle.config.Preference
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class CoreComponentConfig {

  @Bean
  fun vocaDbSongListFetcher(vocaDbSongListApi: VocaDbSongListApi) =
    VocaDbSongListFetcherImpl(vocaDbSongListApi)

  @Bean
  fun mainExporter(vocaDbSongListFetcher: VocaDbSongListFetcher) = MainExporter(vocaDbSongListFetcher)

  @Bean
  fun csvWriter(
    preference: Preference,
    ioConfig: IOConfig
  ): ItemRecorder<VocaDBSongListItem> = if (preference.useVocadbCsvFormat) {
    VocaDbCompatibleSongListCsvProducer(ioConfig.outputCsv)
  } else {
    ToCsvItemRecorder<VocaDBSongListItem>(ioConfig.outputCsv)
  }

  @Bean
  fun mainExporterWithLocalWrite(
    ioConfig: IOConfig,
    mainExporter: MainExporter,
    preference: Preference,
    csvWriter: ItemRecorder<VocaDBSongListItem>
  ) = MainExporterWithLocalWrite(ioConfig, mainExporter, preference, csvWriter)
}