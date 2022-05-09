package com.endava.internship.webapp.service;

import com.endava.internship.webapp.exceptions.DepartmentNotFoundException;
import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.validation.dto.DepartmentDto;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        reset(departmentRepository);
        departmentService = new DepartmentService(departmentRepository);
    }

    @Test
    void getAll_EmptyRepository_ReturnEmptyList() {
        when(departmentRepository.findAll()).thenReturn(List.of());

        List<Department> departments = departmentService.getAll();

        assertThat(departments).isEmpty();
    }

    @Test
    void getOne_ThereIsOneDepartment_ReturnIt() {
        long departmentId = 1L;
        Department d1 = new Department(departmentId, "a1", "a2");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(d1));

        Department returnedD1 = departmentService.getOne(departmentId);

        assertThat(returnedD1).isEqualTo(d1);
    }

    @Test
    void getOne_ThereIsNoDepartment_ThrowException() {
        long departmentId = 1L;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(DepartmentNotFoundException.class)
                .isThrownBy(() -> departmentService.getOne(departmentId))
                .matches(e -> e.getErrors()
                        .get(0)
                        .getValue()
                        .contains(String.valueOf(departmentId)));
    }

    @Test
    void setOne_SaveGoodDepartment_ReturnIt() {
        DepartmentDto d1Dto = new DepartmentDto(1L, "a1", "a2");
        Department d1 = d1Dto.toDepartment();

        when(departmentRepository.save(d1)).thenReturn(d1);

        Department returnedD1 = departmentService.setOne(d1Dto);

        assertThat(returnedD1).isEqualTo(d1);
    }

    @Test
    void replaceOne_ThereIsDepartmentWithSuchId_ReplaceIt() {
        long departmentId = 1L;
        DepartmentDto d1Dto = new DepartmentDto(departmentId, "a1", "a2");
        Department d1 = d1Dto.toDepartment();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(d1));
        when(departmentRepository.save(d1)).thenReturn(d1);

        Department returnedD1 = departmentService.replaceOne(d1Dto, departmentId);

        assertThat(returnedD1).isEqualTo(d1);
    }

    @Test
    void replaceOne_ThereIsNoDepartmentWithSuchId_ThrowException() {
        long departmentId = 2L;
        DepartmentDto d1Dto = new DepartmentDto(1L, "a1", "a2");
        Department d1 = d1Dto.toDepartment();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(d1));

        assertThatExceptionOfType(DepartmentNotFoundException.class)
                .isThrownBy(() -> departmentService.replaceOne(d1Dto, departmentId))
                .matches(e -> e.getErrors().get(0).getValue().contains(String.valueOf(departmentId)));
    }
}