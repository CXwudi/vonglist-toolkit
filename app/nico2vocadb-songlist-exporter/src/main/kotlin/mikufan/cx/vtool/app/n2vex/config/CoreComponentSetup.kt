package mikufan.cx.vtool.app.n2vex.config

import mikufan.cx.vtool.core.n2vex.MainExporter
import mikufan.cx.vtool.core.n2vex.MainExporterWithLocalWrite
import mikufan.cx.vtool.core.n2vex.config.IOConfig
import mikufan.cx.vtool.core.n2vex.config.Preference
import mikufan.cx.vtool.module.model.niconico.NicoListItem
import mikufan.cx.vtool.module.model.vocadb.VocaDBSongListItem
import mikufan.cx.vtool.module.model.vocadb.VocaDbCacheKeys
import mikufan.cx.vtool.service.api.api.NicoListApi
import mikufan.cx.vtool.service.api.api.VocaDbSongByPvApi
import mikufan.cx.vtool.service.api.io.ItemRecorder
import mikufan.cx.vtool.service.impl.NicoListFetcher
import mikufan.cx.vtool.service.impl.ToCsvItemRecorder
import mikufan.cx.vtool.service.impl.VocaDbCompatibleSongListCsvProducer
import mikufan.cx.vtool.service.impl.VocaDbPvMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class CoreComponentSetup {

  @Bean
  fun nicoListFetcher(
    nicoListApi: NicoListApi,
    preference: Preference,
  ) = NicoListFetcher(nicoListApi, preference.usePrivateApi)

  @Bean
  fun vocadbPvMapper(
    vocaDbSongByPvApi: VocaDbSongByPvApi,
    cacheManager: CacheManager,
  ) : VocaDbPvMapper {
    val cache = cacheManager.getCache(VocaDbCacheKeys.SONG_BY_PV)
      ?: throw IllegalStateException("Cache ${VocaDbCacheKeys.SONG_BY_PV} not found")
    return VocaDbPvMapper(vocaDbSongByPvApi, cache)
  }

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
    nicoListFetcher: NicoListFetcher,
    vocadbPvMapper: VocaDbPvMapper,
  ) = MainExporter(nicoListFetcher, vocadbPvMapper)


  @Bean
  fun mainExporterWithLocalWrite(
    ioConfig: IOConfig,
    preference: Preference,
    mainExporter: MainExporter,
    @Qualifier("notFoundCsvRecorder") notFoundCsvRecorder: ToCsvItemRecorder<NicoListItem>,
    @Qualifier("outputCsvRecorder") outputCsvRecorder: ItemRecorder<VocaDBSongListItem>,
  ) = MainExporterWithLocalWrite(
    ioConfig,
    preference,
    mainExporter,
    notFoundCsvRecorder,
    outputCsvRecorder,
  )

}