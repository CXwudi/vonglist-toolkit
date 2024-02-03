package mikufan.cx.vtool.service.api.cache

interface KVCachePersistor<K, V> {
  fun persist(cache: KVCache<K, V>)
  fun load(): KVCache<K, V>
}