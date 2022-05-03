package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.service.DepartmentService;
import com.endava.internship.webapp.validation.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@ResponseBody
@ResponseStatus(HttpStatus.OK)
@RequestMapping("departments")
public class DepartmentController {
    DepartmentService departmentService;


    @GetMapping()
    List<Department> all() {
        return departmentService.getAll();
    }

    @GetMapping("/{departmentId}")
    Department one(@PathVariable Long departmentId) {
        return departmentService.getOne(departmentId);
    }

    @PostMapping()
    Department newDepartment(@Validated @RequestBody DepartmentDto newDepartment) {
        System.out.println(newDepartment);
        return departmentService.setOne(newDepartment);
    }

    @PutMapping("/{departmentId}")
    Department replaceDepartment(
            @Validated @RequestBody DepartmentDto newDepartmentDto,
            @PathVariable Long departmentId) {
        return departmentService.replaceOne(newDepartmentDto, departmentId);
    }


}
