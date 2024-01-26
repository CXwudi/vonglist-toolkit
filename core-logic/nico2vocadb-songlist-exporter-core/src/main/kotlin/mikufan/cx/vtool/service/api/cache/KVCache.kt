package mikufan.cx.vtool.service.api.cache

import java.util.*

/**
 * Our personal key-value cache that support reading and writing all keys and values,
 * and support null value by using [Optional]
 *
 * @param K key
 * @param V value
 */
interface KVCache<K, V> {

  /**
   * try to find mapping V from K
   * @param pv PV
   * @return Optional of V, null means not found,
   * Optional.of(null) means found but no mapping,
   * Optional.of(V) means found and has mapping
   */
  fun tryFindMapping(k: K): Optional<V?>?

  fun recordMapping(k: K, v: V?)

  fun allMappings(): Map<K, Optional<V?>>

  fun setAllMappings(mappings: Map<K, Optional<V?>>)

  fun clear()
}