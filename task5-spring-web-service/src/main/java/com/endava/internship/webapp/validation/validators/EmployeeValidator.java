package com.endava.internship.webapp.validation.validators;

import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.constraints.EmployeeConstraint;
import com.endava.internship.webapp.validation.constraints.EmployeeUniquePhoneNumber;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class EmployeeValidator  implements
        ConstraintValidator<EmployeeConstraint, EmployeeDto> {
    final EmployeeRepository employeeRepository;

    @Override
    public void initialize(EmployeeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EmployeeDto employee, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("+++++++");

        return true;
    }
}
