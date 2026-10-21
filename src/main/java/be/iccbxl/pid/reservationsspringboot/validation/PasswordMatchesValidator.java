package be.iccbxl.pid.reservationsspringboot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordConfirmable> {

    @Override
    public boolean isValid(PasswordConfirmable dto, ConstraintValidatorContext context) {
        String pwd = dto.getPassword();
        String confirm = dto.getConfirmPassword();

        if ((pwd == null || pwd.isBlank()) && (confirm == null || confirm.isBlank())) {
            return true;
        }

        if (pwd == null) {
            return false;
        }

        return pwd.equals(confirm);
    }
}