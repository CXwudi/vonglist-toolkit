package mikufan.cx.vtool.util.infinispan

import org.infinispan.configuration.cache.ConfigurationBuilder
import org.infinispan.configuration.global.GlobalConfigurationBuilder
import java.nio.file.Path
import java.time.Duration
import java.util.concurrent.TimeUnit

fun ConfigurationBuilder.setupPersist(ttl: Duration) = this.persistence()
  .passivation(false)
  .addSoftIndexFileStore()
  .shared(false)
  .preload(true)
  .expiration().lifespan(ttl.toMillis(), TimeUnit.MILLISECONDS)

fun GlobalConfigurationBuilder.setupPersistLocation(dir: Path) = this.globalState().enable()
  .persistentLocation(dir.toString())
  .serialization()