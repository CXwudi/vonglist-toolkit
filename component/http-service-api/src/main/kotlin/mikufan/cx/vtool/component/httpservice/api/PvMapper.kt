package mikufan.cx.vtool.component.httpservice.api

import mikufan.cx.vtool.module.model.vocadb.PV

interface PvMapper {
  fun tryFindRecord(pv: PV): Long?
}