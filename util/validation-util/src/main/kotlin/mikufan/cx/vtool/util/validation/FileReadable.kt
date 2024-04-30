package mikufan.cx.vtool.util.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import java.nio.file.Path
import kotlin.io.path.isReadable
import kotlin.reflect.KClass

@Constraint(validatedBy = [FileReadableValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class FileReadable(
  val message: String = "The file must be readable",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = []
)

class FileReadableValidator : ConstraintValidator<FileReadable, Path> {
  override fun isValid(path: Path, context: ConstraintValidatorContext): Boolean {
    return if (path.isReadable()) {
      true
    } else {
      context.disableDefaultConstraintViolation()
      context.buildConstraintViolationWithTemplate("The file $path is not readable")
        .addConstraintViolation()
      false
    }
  }
}