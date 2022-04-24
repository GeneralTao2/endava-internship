package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.validation.dto.DepartmentDto;
import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {
    DepartmentRepository departmentRepository;

    @GetMapping()
    ResponseEntity<List<Department>> all() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }

    @GetMapping("/{departmentId}")
    ResponseEntity<Optional<Department>> one(@PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentRepository.findById(departmentId));
    }

    @PostMapping()
    ResponseEntity<Department> newDepartment(@RequestBody @Validated DepartmentDto newDepartment) {
        return ResponseEntity.ok(departmentRepository.save(newDepartment.toDepartment()));
    }

    @PutMapping("/{departmentId}")
    ResponseEntity<Department> replaceDepartment(
            @RequestBody @Validated DepartmentDto newDepartmentDto,
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentRepository.findById(departmentId)
                .map(department -> {
                    department.setLocation(newDepartmentDto.getLocation());
                    department.setName(newDepartmentDto.getName());
                    return departmentRepository.save(department);
                }).orElseGet(() -> {
                    newDepartmentDto.setId(departmentId);
                    return departmentRepository.save(newDepartmentDto.toDepartment());
                }));
    }


}
