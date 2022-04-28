package com.endava.internship.webapp.service;

import com.endava.internship.webapp.exceptions.EmployeeNotFoundException;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import com.endava.internship.webapp.validation.validators.db.EmployeeValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@AllArgsConstructor
@Controller
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeValidator employeeValidator;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getOne(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public Employee setOne(@Validated EmployeeDto newEmployeeDto) {
        employeeValidator.validatePostRequestBody(newEmployeeDto);

        return employeeRepository.save(newEmployeeDto.toEmployee());
    }

    public Employee replaceOne(@Validated EmployeeDto newEmployeeDto, Long employeeId) {
        employeeValidator.validatePutRequestBody(newEmployeeDto, employeeId);

        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    employee.setEmail(newEmployeeDto.getEmail());
                    employee.setFirstName(newEmployeeDto.getFirstName());
                    employee.setLastName(newEmployeeDto.getLastName());
                    employee.setPhoneNumber(newEmployeeDto.getPhoneNumber());
                    employee.setSalary(newEmployeeDto.getSalary());
                    employee.setDepartment(
                            departmentRepository.findById(newEmployeeDto.getDepartment().getId())
                                    .orElse(null)
                    );
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }
}
