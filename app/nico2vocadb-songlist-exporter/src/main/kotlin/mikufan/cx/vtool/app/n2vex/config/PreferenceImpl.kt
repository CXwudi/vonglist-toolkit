package mikufan.cx.vtool.app.n2vex.config

import mikufan.cx.vtool.core.n2vex.config.Preference
import mikufan.cx.vtool.module.model.niconico.NicoListSortKey
import mikufan.cx.vtool.module.model.niconico.NicoListSortOrder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "preference")
@Validated
class PreferenceImpl(
  override val sortKey: NicoListSortKey?,
  override val sortOrder: NicoListSortOrder?,
  override val usePrivateApi: Boolean,
  override val useVocadbCsvFormat: Boolean
) : Preference