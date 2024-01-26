package mikufan.cx.vtool.service.api

import mikufan.cx.vtool.module.model.vocadb.PV

fun interface VocaDbPvMapper {
  fun tryFindRecord(pv: PV): Long?
}