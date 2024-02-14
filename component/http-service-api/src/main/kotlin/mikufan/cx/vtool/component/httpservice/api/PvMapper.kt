package mikufan.cx.vtool.component.httpservice.api

import mikufan.cx.vtool.shared.model.vocadb.PV

interface PvMapper {
  fun tryFindRecord(pv: PV): Long?
}