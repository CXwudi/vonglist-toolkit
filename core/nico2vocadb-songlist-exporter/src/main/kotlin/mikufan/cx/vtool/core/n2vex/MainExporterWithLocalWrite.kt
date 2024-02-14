package mikufan.cx.vtool.core.n2vex

import kotlinx.coroutines.Runnable
import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.io.api.ItemRecorder
import mikufan.cx.vtool.core.n2vex.config.IOConfig
import mikufan.cx.vtool.core.n2vex.config.Preference
import mikufan.cx.vtool.shared.model.niconico.NicoListItem
import mikufan.cx.vtool.shared.model.niconico.NicoListSortPreference
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem

class MainExporterWithLocalWrite(
  private val ioConfig: IOConfig,
  preference: Preference,
  private val mainExporter: MainExporter,
  private val notFoundListItemWriter: ItemRecorder<NicoListItem>,
  private val foundListItemWriter: ItemRecorder<VocaDBSongListItem>,
) : Runnable {

  private val sortPreference = NicoListSortPreference(preference.sortKey, preference.sortOrder)

  override fun run() {
    val (notFoundList, foundList) = mainExporter.exportToVocaDbList(ioConfig.nicoListId, sortPreference)
    notFoundListItemWriter.recordAll(notFoundList)
    foundListItemWriter.recordAll(foundList)
    log.info { "Done" }
  }

}


private val log = KInlineLogging.logger()