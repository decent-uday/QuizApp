package com.Uday.quiz.Model.Validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*\\d)(?=.*[@#$&*]).+$");
    }
}
