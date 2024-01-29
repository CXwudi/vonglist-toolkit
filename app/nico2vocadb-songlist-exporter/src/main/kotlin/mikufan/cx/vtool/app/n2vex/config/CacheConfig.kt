package mikufan.cx.vtool.app.n2vex.config

import jakarta.annotation.PreDestroy
import mikufan.cx.vtool.core.n2vex.PvToVocaDbIdCachePersistor
import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.service.api.cache.KVCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class CacheConfig(
  systemConfig: SystemConfig,
) {

  private val cachePersistor = PvToVocaDbIdCachePersistor(systemConfig.pvToVocadbSongMappingCsv)
  private val cache = cachePersistor.load()

  @Bean
  fun pvToVocaDbIdCache(): KVCache<PV, Long> = cache

  @PreDestroy
  fun persist() {
    cachePersistor.persist(cache)
  }
}