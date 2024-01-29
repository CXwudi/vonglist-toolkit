package mikufan.cx.vtool.service.impl

import mikufan.cx.vtool.service.api.cache.KVCache
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock

class InVmKVCache<K, V> : KVCache<K, V> {

  private val cache = mutableMapOf<K, Optional<V?>>()

  private val lock = ReentrantReadWriteLock()

  override fun tryFindMapping(k: K): Optional<V?>? {
    lock.readLock().lock()
    try {
      val cachedOpt = cache[k]
      return cachedOpt
    } finally {
      lock.readLock().unlock()
    }
  }

  override fun recordMapping(k: K, v: V?) {
    lock.writeLock().lock()
    try {
      cache[k] = Optional.ofNullable(v) as Optional<V?>
    } finally {
      lock.writeLock().unlock()
    }
  }

  override fun allMappings(): Map<K, Optional<V?>> {
    lock.readLock().lock()
    try {
      return cache.toMap()
    } finally {
      lock.readLock().unlock()
    }
  }

  override fun setAllMappings(mappings: Map<K, Optional<V?>>) {
    lock.writeLock().lock()
    try {
      cache.clear()
      cache.putAll(mappings)
    } finally {
      lock.writeLock().unlock()
    }
  }

  override fun clear() {
    lock.writeLock().lock()
    try {
      cache.clear()
    } finally {
      lock.writeLock().unlock()
    }
  }
}