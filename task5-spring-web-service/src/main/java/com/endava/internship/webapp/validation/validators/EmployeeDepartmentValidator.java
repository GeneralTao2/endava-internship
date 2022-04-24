package com.endava.internship.webapp.validation.validators;

import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.constraints.EmployeeDepartmentExists;
import com.endava.internship.webapp.validation.constraints.EmployeeUniquePhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class EmployeeDepartmentValidator implements
        ConstraintValidator<EmployeeDepartmentExists, Department> {

    final DepartmentRepository departmentRepository;

    @Override
    public void initialize(EmployeeDepartmentExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Department department, ConstraintValidatorContext constraintValidatorContext) {
        return departmentRepository.existsById(department.getId());
    }
}
