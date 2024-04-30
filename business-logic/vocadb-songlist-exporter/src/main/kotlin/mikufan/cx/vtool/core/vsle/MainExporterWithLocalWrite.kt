package mikufan.cx.vtool.core.vsle

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.io.api.ItemRecorder
import mikufan.cx.vtool.core.vsle.config.IOConfig
import mikufan.cx.vtool.core.vsle.config.Preference
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem

class MainExporterWithLocalWrite(
  private val ioConfig: IOConfig,
  private val mainExporter: MainExporter,
  preference: Preference,
  private val csvWriter: ItemRecorder<VocaDBSongListItem>
) : Runnable {

  private val sort = preference.sortOrder

  override fun run() {
    val list = mainExporter.exportSongList(ioConfig.vocadbListId, sort)
    csvWriter.recordAll(list)
    log.info { "Done" }
  }

}

private val log = KInlineLogging.logger()