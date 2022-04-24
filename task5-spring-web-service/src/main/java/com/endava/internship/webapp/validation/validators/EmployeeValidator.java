package com.endava.internship.webapp.validation.validators;

import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class EmployeeValidator {

    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;

    private final static Map.Entry<String, String> EMAIL_ALREADY_EXISTS =
            new AbstractMap.SimpleEntry<>("email", "Email is already registered");
    private final static Map.Entry<String, String> PHONE_NUMBER_ALREADY_EXISTS =
            new AbstractMap.SimpleEntry<>("phoneNumber", "Phone number is already registered");
    private final static Map.Entry<String, String> DEPARTMENT_NOT_EXISTS =
            new AbstractMap.SimpleEntry<>("department", "Department does not exist");

    public Set<Map.Entry<String, String>> validatePostRequestBody(EmployeeDto newEmployeeDto) {
        Set<Map.Entry<String, String>> errors = new HashSet<>();
        if(!employeeRepository.findByEmail(newEmployeeDto.getEmail()).isEmpty()) {
            errors.add(EMAIL_ALREADY_EXISTS);
        }
        if(!employeeRepository.findByPhoneNumber(newEmployeeDto.getPhoneNumber())
                .isEmpty()) {
            errors.add(PHONE_NUMBER_ALREADY_EXISTS);
        }
        if(!departmentRepository.existsById(newEmployeeDto.getDepartment().getId())) {
            errors.add(DEPARTMENT_NOT_EXISTS);
        }
        return errors;
    }

    public Set<Map.Entry<String, String>> validatePutRequestBody(EmployeeDto newEmployeeDto, Long employeeId) {
        Set<Map.Entry<String, String>> errors = new HashSet<>();
        if(employeeRepository.findByEmail(newEmployeeDto.getEmail()).stream()
                .anyMatch(employee -> employee.getId() != employeeId)
        ) {
            errors.add(EMAIL_ALREADY_EXISTS);
        }
        if(employeeRepository.findByPhoneNumber(newEmployeeDto.getPhoneNumber())
                .stream().anyMatch(employee -> employee.getId() != employeeId)
        ) {
            errors.add(PHONE_NUMBER_ALREADY_EXISTS);
        }
        if(!departmentRepository.existsById(newEmployeeDto.getDepartment().getId())) {
            errors.add(DEPARTMENT_NOT_EXISTS);
        }
        return errors;
    }
}
