package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class DepartmentController {
    DepartmentRepository departmentRepository;

    @GetMapping("/departments")
    List<Department> all() {
        return departmentRepository.findAll();
    }

    @GetMapping("/departments/{departmentId}")
    Optional<Department> one(@PathVariable Long departmentId) {
        return departmentRepository.findById(departmentId);
    }

    @PostMapping("/departments")
    Department newDepartment(@RequestBody Department newDepartment) {
        return departmentRepository.save(newDepartment);
    }

    @PutMapping("/departments/{departmentId}")
    Department replaceDepartment(@RequestBody Department newDepartment,
                                 @PathVariable Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(department -> {
                    department.setLocation(newDepartment.getLocation());
                    department.setName(newDepartment.getName());
                    return departmentRepository.save(department);
                }).orElseGet(() -> {
                    newDepartment.setId(departmentId);
                    return departmentRepository.save(newDepartment);
                });
    }
}
