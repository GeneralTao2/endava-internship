package com.endava.internship.webapp.validation.validators;

import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.constraints.EmployeeUniquePhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class EmployeePhoneNumberValidator implements
        ConstraintValidator<EmployeeUniquePhoneNumber, String> {

    final EmployeeRepository employeeRepository;

    @Override
    public void initialize(EmployeeUniquePhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return employeeRepository.findByPhoneNumber(phoneNumber).isEmpty();
    }
}
