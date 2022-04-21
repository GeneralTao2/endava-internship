package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    List<Employee> all() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    Optional<Employee> one(@PathVariable Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    @PutMapping("/employees/{employeeId}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    employee.setEmail(newEmployee.getEmail());
                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setLastName(newEmployee.getLastName());
                    employee.setPhoneNumber(newEmployee.getPhoneNumber());
                    employee.setSalary(newEmployee.getSalary());
                    employee.setDepartment(newEmployee.getDepartment());
                    return employeeRepository.save(employee);
                }).orElseGet(() -> {
                    newEmployee.setId(employeeId);
                    return employeeRepository.save(newEmployee);
                });
    }

}
