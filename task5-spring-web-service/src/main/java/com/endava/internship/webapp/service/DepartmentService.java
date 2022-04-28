package com.endava.internship.webapp.service;

import com.endava.internship.webapp.exceptions.DepartmentNotFoundException;
import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.validation.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@AllArgsConstructor
@Component
public class DepartmentService {
    DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getOne(Long departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(
                () -> new DepartmentNotFoundException(departmentId)
        );
    }

    public Department setOne(@Validated DepartmentDto department) {
        return departmentRepository.save(department.toDepartment());
    }

    public Department replaceOne(@Validated DepartmentDto newDepartment, Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(department -> {
                    department.setLocation(newDepartment.getLocation());
                    department.setName(newDepartment.getName());
                    return departmentRepository.save(department);
                }).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
    }
}
