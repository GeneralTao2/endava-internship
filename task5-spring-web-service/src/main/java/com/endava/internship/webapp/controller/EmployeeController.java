package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.validation.dto.EmployeeDto;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @GetMapping()
    ResponseEntity<List<Employee>> all() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @GetMapping("/{employeeId}")
    ResponseEntity<Optional<Employee>> one(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeRepository.findById(employeeId));
    }

    @PostMapping()
    ResponseEntity<Employee> newEmployee(@RequestBody @Validated EmployeeDto newEmployeeDto) {
        return ResponseEntity.ok(employeeRepository.save(newEmployeeDto.toEmployee()));
    }

    @PutMapping("/{employeeId}")
    ResponseEntity<Employee> replaceEmployee(
            @RequestBody @Validated EmployeeDto newEmployeeDto,
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeRepository.findById(employeeId)
                .map(employee -> {
                    employee.setEmail(newEmployeeDto.getEmail());
                    employee.setFirstName(newEmployeeDto.getFirstName());
                    employee.setLastName(newEmployeeDto.getLastName());
                    employee.setPhoneNumber(newEmployeeDto.getPhoneNumber());
                    employee.setSalary(newEmployeeDto.getSalary());
                    employee.setDepartment(newEmployeeDto.getDepartment());
                    return employeeRepository.save(employee);
                }).orElseGet(() -> {
                    newEmployeeDto.setId(employeeId);
                    return employeeRepository.save(newEmployeeDto.toEmployee());
                }));
    }

}
