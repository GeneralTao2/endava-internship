package com.endava.internship.webapp.validation.validators.constraint;

import com.endava.internship.webapp.validation.validators.annotations.ClearNotBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClearNotBlankValidator implements
        ConstraintValidator<ClearNotBlank, String> {
    @Override
    public void initialize(ClearNotBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.length() > 0;
    }
}
