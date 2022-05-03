package com.endava.internship.webapp.validation.constraints;


import com.endava.internship.webapp.validation.validators.constraint.ClearNotBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClearNotBlankValidator.class)
public @interface ClearNotBlank {
    String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
