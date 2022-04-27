package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.exceptions.ErrorResponse;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import com.endava.internship.webapp.validation.validators.db.EmployeeValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeValidator employeeValidator;

    @GetMapping()
    ResponseEntity<List<Employee>> all() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @GetMapping("/{employeeId}")
    ResponseEntity<Optional<Employee>> one(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeRepository.findById(employeeId));
    }

    @PostMapping()
    ResponseEntity<?> newEmployee(
            @RequestBody @Validated EmployeeDto newEmployeeDto) {

        UriComponents uri = MvcUriComponentsBuilder.fromController(getClass())
                .build();

        List<Map.Entry<String, String>> errors = employeeValidator.validatePostRequestBody(newEmployeeDto);

        if (errors.isEmpty()) {
            return ResponseEntity
                    .ok(employeeRepository.save(newEmployeeDto.toEmployee()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            errors,
                            uri.getPath()));
        }
    }

    @PutMapping("/{employeeId}")
    ResponseEntity<?> replaceEmployee(
            @RequestBody @Validated EmployeeDto newEmployeeDto,
            @PathVariable Long employeeId) {

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{employeeId}").build(employeeId);

        List<Map.Entry<String, String>> errors = employeeValidator.validatePutRequestBody(newEmployeeDto, employeeId);

        if (errors.isEmpty()) {
            return ResponseEntity.ok(employeeRepository.findById(employeeId)
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
                    }).orElseGet(() -> {
                        // TODO: do not really save `employee` with id `employeeId`
                        newEmployeeDto.setId(employeeId);
                        newEmployeeDto.setDepartment(
                                departmentRepository.findById(newEmployeeDto.getDepartment().getId())
                                        .orElse(null)
                        );
                        return employeeRepository.save(newEmployeeDto.toEmployee());
                    }));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            errors,
                            uri.getPath()));
        }

    }

}
