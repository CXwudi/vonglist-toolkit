package mikufan.cx.vtool.app.n2vex.config

import mikufan.cx.vtool.shared.model.AllCacheNames
import org.infinispan.configuration.cache.ConfigurationBuilder
import org.infinispan.configuration.global.GlobalConfigurationBuilder
import org.infinispan.jcache.embedded.JCacheManager
import org.infinispan.manager.EmbeddedCacheManager
import org.infinispan.spring.starter.embedded.InfinispanCacheConfigurer
import org.infinispan.spring.starter.embedded.InfinispanGlobalConfigurationCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Path
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfig {

  @Bean
  fun enableCustomPersistentLocation(
    cacheConfigProperties: CacheConfigProperties,
  ) = EnableCustomPersistentLocationGlobalConfigCustomizer(cacheConfigProperties.dir)

  @Bean
  fun enableCachePersistent(
    cacheConfigProperties: CacheConfigProperties,
  ): EnablePersistentCacheManagerConfigurer {
    return EnablePersistentCacheManagerConfigurer(cacheConfigProperties.ttl, AllCacheNames())
  }

  @Bean
  fun jCacheManager(infinispanCacheManager: EmbeddedCacheManager) = JCacheManager(null, infinispanCacheManager, null)
}

class EnableCustomPersistentLocationGlobalConfigCustomizer(
  private val dir: Path,
) : InfinispanGlobalConfigurationCustomizer {

  override fun customize(builder: GlobalConfigurationBuilder) {
    builder.globalState().enable()
      .persistentLocation(dir.toString())
      .serialization()
  }
}

class EnablePersistentCacheManagerConfigurer(
  ttl: Duration,
  private val cacheNames: List<String>,
) : InfinispanCacheConfigurer {
  private val configuration = ConfigurationBuilder()
    .persistence().passivation(false)
    .addSoftIndexFileStore()
    .shared(false)
    .preload(true)
    .expiration().lifespan(ttl.toMillis(), TimeUnit.MILLISECONDS)
    .build()

  override fun configureCache(manager: EmbeddedCacheManager) {
    cacheNames.forEach { manager.defineConfiguration(it, configuration) }
  }
}
