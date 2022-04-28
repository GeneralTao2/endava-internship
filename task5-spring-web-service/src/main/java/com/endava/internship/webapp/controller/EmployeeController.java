package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.service.EmployeeService;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@ResponseBody
@ResponseStatus(HttpStatus.OK)
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping()
    List<Employee> all() {
        return employeeService.getAll();
    }

    @GetMapping("/{employeeId}")
    Employee one(@PathVariable Long employeeId) {
        return employeeService.getOne(employeeId);
    }

    @PostMapping()
    Employee newEmployee(@RequestBody EmployeeDto newEmployeeDto) {
        return employeeService.setOne(newEmployeeDto);
    }

    @PutMapping("/{employeeId}")
    Employee replaceEmployee(
            @RequestBody EmployeeDto newEmployeeDto,
            @PathVariable Long employeeId) {

        return employeeService.replaceOne(newEmployeeDto, employeeId);

    }

}
