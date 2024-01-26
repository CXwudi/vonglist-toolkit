package mikufan.cx.vtool.service.impl

import mikufan.cx.vtool.module.model.vocadb.PV
import mikufan.cx.vtool.service.api.cache.PvToVocaDbIdCache
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock

class InVMPvToVocaDbIdCache : PvToVocaDbIdCache {

  private val cache = mutableMapOf<PV, Optional<Long?>>()

  private val lock = ReentrantReadWriteLock()

  override fun tryFindMapping(k: PV): Optional<Long?>? {
    lock.readLock().lock()
    try {
      val cachedOpt = cache[k]
      return cachedOpt
    } finally {
      lock.readLock().unlock()
    }
  }

  override fun recordMapping(k: PV, v: Long?) {
    lock.writeLock().lock()
    try {
      cache[k] = Optional.ofNullable(v) as Optional<Long?>
    } finally {
      lock.writeLock().unlock()
    }
  }

  override fun allMappings(): Map<PV, Optional<Long?>> {
    lock.readLock().lock()
    try {
      return cache.toMap()
    } finally {
      lock.readLock().unlock()
    }
  }

  override fun setAllMappings(mappings: Map<PV, Optional<Long?>>) {
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