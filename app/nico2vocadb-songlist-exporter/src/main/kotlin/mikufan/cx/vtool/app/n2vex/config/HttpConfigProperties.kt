package mikufan.cx.vtool.app.n2vex.config

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
import kotlin.reflect.KClass

@ConfigurationProperties(prefix = "system.http")
@Validated
@IsLogon
data class HttpConfigProperties(
  val cookieJarTxt: Path?,
  val niconicoUserSessionCookieValue: String?,
  @get:NotBlank
  val userAgent: String,
)

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
) : ConstraintValidator<IsLogon, HttpConfigProperties> {

    override fun isValid(systemConfigProperties: HttpConfigProperties, context: ConstraintValidatorContext): Boolean = if (requireLogon) {
      systemConfigProperties.cookieJarTxt != null || systemConfigProperties.niconicoUserSessionCookieValue?.isNotBlank() ?: false
    } else {
      true
    }
}