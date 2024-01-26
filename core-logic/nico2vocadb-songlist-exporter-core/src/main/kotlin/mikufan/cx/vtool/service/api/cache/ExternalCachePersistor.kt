package mikufan.cx.vtool.service.api.cache

interface ExternalCachePersistor {

  fun persist()

  fun read()
}