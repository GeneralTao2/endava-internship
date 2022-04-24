package com.endava.internship.webapp.validation.constraints;

import com.endava.internship.webapp.validation.validators.EmployeeDepartmentValidator;
import com.endava.internship.webapp.validation.validators.EmployeePhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeDepartmentValidator.class)
public @interface EmployeeDepartmentExists {
    String message() default "Department does not exist";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
