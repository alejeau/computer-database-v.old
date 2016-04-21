package com.excilys.formation.computerdb.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.excilys.formation.computerdb.validators.DateValidator;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Invalid Date format, please enter a 'yyyy-MM-dd' format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
