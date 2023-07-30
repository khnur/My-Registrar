package com.example.myregistrar.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
@Documented
public @interface ValidRole {
    String message() default "Invalid role format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
