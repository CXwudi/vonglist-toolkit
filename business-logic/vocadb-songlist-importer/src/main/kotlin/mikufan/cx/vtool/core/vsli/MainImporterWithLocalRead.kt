package mikufan.cx.vtool.core.vsli

import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.io.api.ItemReader
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem

class MainImporterWithLocalRead(
  private val vocadbListItemReader: ItemReader<VocaDBSongListItem>,
  private val vocaDbListId: Long? = null,
  private val mainImporter: MainImporter,
) : Runnable {

  override fun run() {
    val vocaDBSongListItems = vocadbListItemReader.readAll()
    mainImporter.importToVocaDb(vocaDBSongListItems, vocaDbListId)
    log.info { "Done" }
  }
}

private val log = KInlineLogging.logger()