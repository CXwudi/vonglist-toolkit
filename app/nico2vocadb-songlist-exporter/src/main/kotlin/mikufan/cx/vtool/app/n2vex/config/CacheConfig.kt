package mikufan.cx.vtool.app.n2vex.config

import com.fasterxml.jackson.databind.ObjectMapper
import mikufan.cx.vtool.module.model.AllCacheNames
import org.infinispan.commons.dataconversion.MediaType
import org.infinispan.commons.io.ByteBuffer
import org.infinispan.commons.io.ByteBufferImpl
import org.infinispan.commons.marshall.AbstractMarshaller
import org.infinispan.configuration.cache.ConfigurationBuilder
import org.infinispan.configuration.global.GlobalConfigurationBuilder
import org.infinispan.jcache.embedded.JCacheManager
import org.infinispan.manager.EmbeddedCacheManager
import org.infinispan.spring.starter.embedded.InfinispanCacheConfigurer
import org.infinispan.spring.starter.embedded.InfinispanGlobalConfigurationCustomizer
import org.springframework.boot.autoconfigure.cache.CacheProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration(proxyBeanMethods = false)
class CacheConfig {

  @Bean
  fun jacksonJsonNodeMarshaller(objectMapper: ObjectMapper) = JacksonJsonNodeMarshaller(objectMapper)

  @Bean
  fun enableCustomPersistentLocation(
    cacheConfigProperties: CacheConfigProperties,
    jsonNodeMarshaller: JacksonJsonNodeMarshaller,
  ) = EnableCustomPersistentLocationGlobalConfigCustomizer(cacheConfigProperties, jsonNodeMarshaller)

  @Bean
  fun enableCachePersistent(
    cacheConfigProperties: CacheConfigProperties,
    springCacheConfigProperties: CacheProperties,
  ) = EnablePersistentCacheManagerConfigurer(cacheConfigProperties, springCacheConfigProperties)

  @Bean
  fun jCacheManager(infinispanCacheManager: EmbeddedCacheManager) = JCacheManager(null, infinispanCacheManager, null)
}

class EnableCustomPersistentLocationGlobalConfigCustomizer(
  cacheConfigProperties: CacheConfigProperties,
  private val jsonNodeMarshaller: JacksonJsonNodeMarshaller,
) : InfinispanGlobalConfigurationCustomizer {

  private val dir = cacheConfigProperties.dir
  override fun customize(builder: GlobalConfigurationBuilder) {
    builder.globalState().enable()
      .persistentLocation(dir.toString())
      .serialization().apply {
        marshaller(jsonNodeMarshaller).allowList().addRegexps<Any>("com\\.fasterxml\\.jackson.*")
      }
  }
}

class EnablePersistentCacheManagerConfigurer(
  cacheConfigProperties: CacheConfigProperties,
  springCacheConfigProperties: CacheProperties,
) : InfinispanCacheConfigurer {
  private val configuration = ConfigurationBuilder()
    .persistence().passivation(false)
    .addSoftIndexFileStore()
    .shared(false)
    .preload(true)
    .expiration().lifespan(cacheConfigProperties.ttl.toMillis(), TimeUnit.MILLISECONDS)
    .build()

  private val cacheNames = springCacheConfigProperties.cacheNames

  override fun configureCache(manager: EmbeddedCacheManager) {
    AllCacheNames().forEach { manager.defineConfiguration(it, configuration) }
    cacheNames.forEach { manager.defineConfiguration(it, configuration) }
  }
}

class JacksonJsonNodeMarshaller(
  private val objectMapper: ObjectMapper,
) : AbstractMarshaller() {

  override fun objectFromByteBuffer(buf: ByteArray?, offset: Int, length: Int): Any {
    return objectMapper.readTree(buf)
  }

  override fun objectToBuffer(o: Any?, estimatedSize: Int): ByteBuffer {
    val bytes = objectMapper.writeValueAsBytes(o)
    return ByteBufferImpl.create(bytes)
  }

  override fun isMarshallable(o: Any?): Boolean {
    return o != null
  }

  override fun mediaType(): MediaType {
    return MediaType.APPLICATION_JSON
  }
}