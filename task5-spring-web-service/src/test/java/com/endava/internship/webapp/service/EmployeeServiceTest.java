package com.endava.internship.webapp.service;

import com.endava.internship.webapp.exceptions.EmployeeNotFoundException;
import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import com.endava.internship.webapp.validation.validators.db.EmployeeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    EmployeeValidator employeeValidator;

    EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        reset(employeeRepository, departmentRepository, employeeValidator);
        employeeService = new EmployeeService(employeeRepository, departmentRepository, employeeValidator);
    }

    @Test
    void getAll_EmptyRepository_ReturnEmptyList() {
        when(employeeRepository.findAll()).thenReturn(List.of());

        List<Employee> employees = employeeService.getAll();

        assertThat(employees).isEmpty();
    }

    @Test
    void getOne_ThereIsOneEmployee_ReturnIt() {
        long employeeId = 1L;
        Department d1 = new Department(1L, "a1", "a2");
        Employee e1 = new Employee(employeeId, "a1", "b1", d1,
                "e@w.w1", "123", 2L);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(e1));

        Employee returnedEmployee = employeeService.getOne(employeeId);

        assertThat(returnedEmployee).isEqualTo(e1);
    }

    @Test
    void getOne_ThereIsNoEmployee_ThrowException() {
        long employeeId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.getOne(employeeId))
                .matches(e -> e.getErrors()
                        .get(0)
                        .getValue()
                        .contains(String.valueOf(employeeId)));
    }

    @Test
    void setOne_SaveGoodEmployee_ReturnIt() {
        Department d1 = new Department(1L, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", d1,
                "e@w.w1", "123", 2L);
        Employee e1 = e1Dto.toEmployee();

        when(employeeRepository.save(e1)).thenReturn(e1);

        Employee returnedEmployee = employeeService.setOne(e1Dto);

        verify(employeeValidator).validatePostRequestBody(e1Dto);

        assertThat(returnedEmployee).isEqualTo(e1);
    }

    @Test
    void replaceOne_ThereIsEmployeeWithSuchId_ReplaceAndReturnIt() {
        long employeeId = 1L;
        long departmentId = 1L;
        Department d1 = new Department(departmentId, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(employeeId, "a1", "b1", d1,
                "e@w.w1", "123", 2L);
        Employee e1 = e1Dto.toEmployee();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(e1));
        when(employeeRepository.save(e1)).thenReturn(e1);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(d1));

        Employee returnedEmployee = employeeService.replaceOne(e1Dto, employeeId);

        verify(employeeValidator).validatePutRequestBody(e1Dto, employeeId);

        assertThat(returnedEmployee).isEqualTo(e1);
    }

    @Test
    void replaceOne_ThereIsNoEmployeeWithSuchId_ThrowException() {
        long employeeId = 1L;
        long departmentId = 1L;
        Department d1 = new Department(departmentId, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(employeeId, "a1", "b1", d1,
                "e@w.w1", "123", 2L);
        Employee e1 = e1Dto.toEmployee();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(e1));

        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.replaceOne(e1Dto, employeeId))
                .matches(e -> e.getErrors()
                        .get(0)
                        .getValue()
                        .contains(String.valueOf(employeeId)));

        verify(employeeValidator).validatePutRequestBody(e1Dto, employeeId);
    }
}