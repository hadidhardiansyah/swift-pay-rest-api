package com.hadid.swiftpay.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isBlank()) {
            return false;
        }

        return password.matches(PASSWORD_PATTERN);
    }
}
