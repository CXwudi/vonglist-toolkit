package mikufan.cx.vtool.app.n2vex.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import mikufan.cx.vtool.component.httpser.api.NicoListFetcher
import mikufan.cx.vtool.component.httpser.api.PvMapper
import mikufan.cx.vtool.component.httpser.impl.NicoListFetcherImpl
import mikufan.cx.vtool.component.httpser.impl.VocaDbPvMapper
import mikufan.cx.vtool.component.httpser.impl.api.NicoListApi
import mikufan.cx.vtool.component.httpser.impl.api.VocaDbSongByPvApi
import mikufan.cx.vtool.component.io.api.ItemRecorder
import mikufan.cx.vtool.component.io.impl.ToCsvItemRecorder
import mikufan.cx.vtool.component.io.impl.VocaDbCompatibleSongListCsvProducer
import mikufan.cx.vtool.core.n2vex.MainExporter
import mikufan.cx.vtool.core.n2vex.MainExporterWithLocalWrite
import mikufan.cx.vtool.core.n2vex.config.IOConfig
import mikufan.cx.vtool.core.n2vex.config.Preference
import mikufan.cx.vtool.shared.model.niconico.NicoListItem
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem
import mikufan.cx.vtool.shared.model.vocadb.VocaDbCacheKeys
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

@Configuration(proxyBeanMethods = false)
class CoreComponentSetup {

  @Bean
  fun loomDispatcher(): CoroutineDispatcher = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()

  @Bean
  fun nicoListFetcher(
    nicoListApi: NicoListApi,
    preference: Preference,
  ): NicoListFetcher = NicoListFetcherImpl(nicoListApi, preference.usePrivateApi)

  @Bean
  fun vocadbPvMapper(
    vocaDbSongByPvApi: VocaDbSongByPvApi,
    cacheManager: CacheManager,
  ) : PvMapper {
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
    vocadbPvMapper: PvMapper,
    loomDispatcher: CoroutineDispatcher,
  ) = MainExporter(nicoListFetcher, vocadbPvMapper, loomDispatcher)


  @Bean
  fun mainExporterWithLocalWrite(
    ioConfig: IOConfig,
    preference: Preference,
    mainExporter: MainExporter,
    @Qualifier("notFoundCsvRecorder") notFoundCsvRecorder: ItemRecorder<NicoListItem>,
    @Qualifier("outputCsvRecorder") outputCsvRecorder: ItemRecorder<VocaDBSongListItem>,
  ) = MainExporterWithLocalWrite(
    ioConfig,
    preference,
    mainExporter,
    notFoundCsvRecorder,
    outputCsvRecorder,
  )

}