package mikufan.cx.vtool.app.vsle.config

import mikufan.cx.vtool.core.vsle.config.Preference
import mikufan.cx.vtool.shared.model.vocadb.VocaDbSongListItemSortOrder
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "preference")
data class PreferenceImpl(
  override val sortOrder: VocaDbSongListItemSortOrder?,
  override val useVocadbCsvFormat: Boolean
) : Preference
