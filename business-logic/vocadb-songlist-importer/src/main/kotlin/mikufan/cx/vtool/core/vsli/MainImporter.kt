package mikufan.cx.vtool.core.vsli

import com.fasterxml.jackson.databind.JsonNode
import mikufan.cx.inlinelogging.KInlineLogging
import mikufan.cx.vtool.component.httpser.api.VocaDbSongListPusher
import mikufan.cx.vtool.core.vsli.component.JsonHandles
import mikufan.cx.vtool.shared.model.vocadb.VocaDBSongListItem

class MainImporter(
  private val jsonHandles: JsonHandles,
  private val vocaDbSongListPusher: VocaDbSongListPusher,
) {

  fun importToVocaDb(songItems: List<VocaDBSongListItem>, toListId: Long? = null) {
    val json: JsonNode = if (toListId == null) {
      jsonHandles.createNewListJson()
    } else {
      vocaDbSongListPusher.getForEditById(toListId)
    }
    jsonHandles.setSongListToJson(json, songItems)
    val id = vocaDbSongListPusher.pushSongList(json)
    log.info { "Successfully pushed song list to VocaDB with id $id" }
  }
}

private val log = KInlineLogging.logger()
