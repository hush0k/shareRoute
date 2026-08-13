package com.hush0k.shareRoute.auth.internal.annotations;

import com.hush0k.shareRoute.auth.internal.dto.request.RegisterDataDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterDataDto> {

    @Override
    public boolean isValid(RegisterDataDto value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }

        boolean matches = Objects.equals(value.password(), value.confirmPassword());

        if(!matches){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("auth.passwords.notMatch")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }
        return matches;
    }

}
