package mikufan.cx.vtool.app.n2vex.config

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import mikufan.cx.vtool.core.n2vex.config.Preference
import mikufan.cx.vtool.shared.model.niconico.NicoListSortKey
import mikufan.cx.vtool.shared.model.niconico.NicoListSortOrder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import kotlin.reflect.KClass

@ConfigurationProperties(prefix = "preference")
@Validated
@SortKeyAndOrder
class PreferenceImpl(
  override val sortKey: NicoListSortKey?,
  override val sortOrder: NicoListSortOrder?,
  override val usePrivateApi: Boolean,
  override val useVocadbCsvFormat: Boolean
) : Preference

@Constraint(validatedBy = [SortKeyAndOrderValidator::class])
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class SortKeyAndOrder(
  val message: String = "sortKey and sortOrder must be either both null or both not null",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = []
)

class SortKeyAndOrderValidator : ConstraintValidator<SortKeyAndOrder, Preference> {

    override fun isValid(preference: Preference, context: ConstraintValidatorContext): Boolean {
        return (preference.sortKey == null && preference.sortOrder == null) ||
               (preference.sortKey != null && preference.sortOrder != null)
    }
}