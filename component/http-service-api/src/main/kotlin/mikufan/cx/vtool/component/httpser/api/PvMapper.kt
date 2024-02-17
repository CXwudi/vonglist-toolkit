package mikufan.cx.vtool.component.httpser.api

import mikufan.cx.vtool.shared.model.vocadb.PV

interface PvMapper {
  fun tryFindRecord(pv: PV): Long?
}