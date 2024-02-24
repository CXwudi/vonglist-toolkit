package mikufan.cx.vtool.app.vsli.config

import mikufan.cx.vtool.core.vsli.config.Preference
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "preference")
@Validated
class PreferenceImpl(
  override val useVocadbCsvFormat: Boolean
) : Preference