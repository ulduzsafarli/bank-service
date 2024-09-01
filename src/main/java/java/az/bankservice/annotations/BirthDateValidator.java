package java.az.bankservice.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {

    @Override
    public void initialize(BirthDate constraintAnnotation) {
        /*
            No initialization is necessary for this annotation
         */
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Birthdate must not be null").addConstraintViolation();
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        return age >= 18;
    }
}
