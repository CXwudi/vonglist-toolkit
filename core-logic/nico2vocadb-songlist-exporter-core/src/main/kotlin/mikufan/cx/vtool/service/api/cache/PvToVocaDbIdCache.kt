package mikufan.cx.vtool.service.api.cache

import mikufan.cx.vtool.module.model.vocadb.PV

interface PvToVocaDbIdCache : KVCache<PV, Long>