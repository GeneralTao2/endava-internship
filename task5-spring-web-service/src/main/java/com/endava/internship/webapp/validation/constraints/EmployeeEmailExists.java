package com.endava.internship.webapp.validation.constraints;

import com.endava.internship.webapp.validation.validators.EmployeeDepartmentValidator;
import com.endava.internship.webapp.validation.validators.EmployeeEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeEmailValidator.class)
public @interface EmployeeEmailExists {
    String message() default "Email is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
