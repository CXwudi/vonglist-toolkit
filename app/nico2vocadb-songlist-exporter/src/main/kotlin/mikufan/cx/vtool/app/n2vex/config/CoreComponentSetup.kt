package mikufan.cx.vtool.app.n2vex.config

import mikufan.cx.vtool.core.n2vex.MainExporter
import mikufan.cx.vtool.core.n2vex.config.IOConfig
import mikufan.cx.vtool.core.n2vex.config.Preference
import mikufan.cx.vtool.module.model.niconico.NicoListItem
import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.module.model.vocadb.VocaDBSongListItem
import mikufan.cx.vtool.service.api.api.NicoListApi
import mikufan.cx.vtool.service.api.api.VocaDbSongByPvApi
import mikufan.cx.vtool.service.api.cache.KVCache
import mikufan.cx.vtool.service.api.io.ItemRecorder
import mikufan.cx.vtool.service.impl.ApiBasedNicoListFetcher
import mikufan.cx.vtool.service.impl.ApiBasedVocaDbPvMapper
import mikufan.cx.vtool.service.impl.ToCsvItemRecorder
import mikufan.cx.vtool.service.impl.VocaDbCompatibleSongListCsvProducer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class CoreComponentSetup {

  @Bean
  fun nicoListFetcher(
    nicoListApi: NicoListApi,
    preference: Preference,
  ) = ApiBasedNicoListFetcher(nicoListApi, preference.usePrivateApi)

  @Bean
  fun vocadbPvMapper(
    vocaDbSongByPvApi: VocaDbSongByPvApi,
    cache: KVCache<PV, Long>,
  ) = ApiBasedVocaDbPvMapper(vocaDbSongByPvApi, cache)

  @Bean
  fun notFoundCsvRecorder(
    ioConfig: IOConfig,
  ) = ToCsvItemRecorder<NicoListItem>(ioConfig.notFoundCsv)

  @Bean
  fun outputCsvRecorder(
    ioConfig: IOConfig,
    preference: Preference,
  ) = if (preference.useVocadbCsvFormat) {
    VocaDbCompatibleSongListCsvProducer(ioConfig.outputCsv)
  } else {
    ToCsvItemRecorder<VocaDBSongListItem>(ioConfig.outputCsv)
  }


  @Bean
  fun mainExporter(
    ioConfig: IOConfig,
    preference: Preference,
    nicoListFetcher: ApiBasedNicoListFetcher,
    vocadbPvMapper: ApiBasedVocaDbPvMapper,
    @Qualifier("notFoundCsvRecorder") notFoundCsvRecorder: ToCsvItemRecorder<NicoListItem>,
    @Qualifier("outputCsvRecorder") outputCsvRecorder: ItemRecorder<VocaDBSongListItem>,
  ) = MainExporter(
    ioConfig,
    preference,
    nicoListFetcher,
    vocadbPvMapper,
    notFoundCsvRecorder,
    outputCsvRecorder,
  )

}