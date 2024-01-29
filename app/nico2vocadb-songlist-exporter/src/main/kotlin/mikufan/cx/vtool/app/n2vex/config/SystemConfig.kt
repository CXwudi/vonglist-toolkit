package mikufan.cx.vtool.app.n2vex.config

import jakarta.annotation.PostConstruct
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import jakarta.validation.constraints.NotBlank
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists
import kotlin.reflect.KClass

@ConfigurationProperties(prefix = "system")
@Validated
@IsLogon
data class SystemConfig(
  val cookieJarTxt: Path?,
  val niconicoUserSessionCookieValue: String?,
  val pvToVocadbSongMappingCsv: Path,
  @get:NotBlank
  val userAgent: String,
) {

  @PostConstruct
  fun createFiles() {
    if (pvToVocadbSongMappingCsv.parent?.notExists() == true) {
      pvToVocadbSongMappingCsv.parent?.createDirectories()
    }
  }
}


@Constraint(validatedBy = [IsLogonValidator::class])
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsLogon(
  val message: String = "Either cookieJarTxt or niconicoUserSessionCookieValue must be set",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = []
)


@Component
class IsLogonValidator(
  @Value("\${preference.use-private-api:false}")
  private val requireLogon: Boolean
) : ConstraintValidator<IsLogon, SystemConfig> {

    override fun isValid(systemConfig: SystemConfig, context: ConstraintValidatorContext): Boolean = if (requireLogon) {
      systemConfig.cookieJarTxt != null || systemConfig.niconicoUserSessionCookieValue?.isNotBlank() ?: false
    } else {
      true
    }
}