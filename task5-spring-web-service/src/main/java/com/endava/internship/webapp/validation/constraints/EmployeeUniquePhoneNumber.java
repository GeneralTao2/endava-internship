package com.endava.internship.webapp.validation.constraints;

import com.endava.internship.webapp.validation.validators.EmployeePhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeePhoneNumberValidator.class)
public @interface EmployeeUniquePhoneNumber {
    String message() default "Phone number is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
