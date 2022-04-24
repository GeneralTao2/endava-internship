package com.endava.internship.webapp.validation.validators;

import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.constraints.EmployeeEmailExists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class EmployeeEmailValidator implements
        ConstraintValidator<EmployeeEmailExists, String> {

    final EmployeeRepository employeeRepository;

    @Override
    public void initialize(EmployeeEmailExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return employeeRepository.findByEmail(email).isEmpty();
    }
}
