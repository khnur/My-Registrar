package com.example.myregistrar.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    @Override
    public void initialize(ValidRole constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String val, ConstraintValidatorContext constraintValidatorContext) {
        return val == null || val.toUpperCase().startsWith("ROLE_");
    }
}
