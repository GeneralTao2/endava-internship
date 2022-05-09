package com.endava.internship.webapp.validation.validators.db;

import com.endava.internship.webapp.exceptions.DtoDbFieldsNotValidException;
import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class EmployeeValidator {

    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;

    public final static Map.Entry<String, String> EMAIL_ALREADY_EXISTS =
            new AbstractMap.SimpleEntry<>("email", "Email is already registered");
    public final static Map.Entry<String, String> PHONE_NUMBER_ALREADY_EXISTS =
            new AbstractMap.SimpleEntry<>("phoneNumber", "Phone number is already registered");
    public final static Map.Entry<String, String> DEPARTMENT_NOT_EXISTS =
            new AbstractMap.SimpleEntry<>("department", "Department does not exist");

    public void validatePostRequestBody(EmployeeDto newEmployeeDto) {
        List<Map.Entry<String, String>> errors = new ArrayList<>();

        if (thereIsEmployeeWithEmail(newEmployeeDto.getEmail())) {
            errors.add(EMAIL_ALREADY_EXISTS);
        }
        if (thereIsEmployeeWithPhoneNumber(newEmployeeDto.getPhoneNumber())) {
            errors.add(PHONE_NUMBER_ALREADY_EXISTS);
        }
        if (departmentNotExist(newEmployeeDto.getDepartment())) {
            errors.add(DEPARTMENT_NOT_EXISTS);
        }

        if(!errors.isEmpty()) {
            throw new DtoDbFieldsNotValidException(errors);
        }
    }


    public void validatePutRequestBody(EmployeeDto newEmployeeDto, Long employeeId) {
        List<Map.Entry<String, String>> errors = new ArrayList<>();

        if (thereIsOtherEmployeeWithEmail(newEmployeeDto.getEmail(), employeeId)) {
            errors.add(EMAIL_ALREADY_EXISTS);
        }
        if (thereIsOtherEmployeeWithPhoneNumber(newEmployeeDto.getPhoneNumber(), employeeId)) {
            errors.add(PHONE_NUMBER_ALREADY_EXISTS);
        }
        if (departmentNotExist(newEmployeeDto.getDepartment())) {
            errors.add(DEPARTMENT_NOT_EXISTS);
        }

        if(!errors.isEmpty()) {
            throw new DtoDbFieldsNotValidException(errors);
        }
    }

    private boolean departmentNotExist(Department department) {
        return department == null ||
                !departmentRepository.existsById(department.getId());
    }

    private boolean thereIsEmployeeWithPhoneNumber(String phoneNumber) {
        return !employeeRepository.findByPhoneNumber(phoneNumber).isEmpty();
    }

    private boolean thereIsEmployeeWithEmail(String email) {
        return !employeeRepository.findByEmail(email).isEmpty();
    }

    private boolean thereIsOtherEmployeeWithPhoneNumber(String phoneNumber, Long employeeId) {
        return employeeRepository.findByPhoneNumber(phoneNumber)
                .stream().anyMatch(employee -> employee.getId() != employeeId);
    }

    private boolean thereIsOtherEmployeeWithEmail(String email, Long employeeId) {
        return employeeRepository.findByEmail(email).stream()
                .anyMatch(employee -> employee.getId() != employeeId);
    }
}
