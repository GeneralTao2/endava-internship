package com.endava.internship.webapp.validation.validators.db;

import com.endava.internship.webapp.exceptions.DtoDbFieldsNotValidException;
import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeValidatorTest {

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    EmployeeRepository employeeRepository;

    EmployeeValidator employeeValidator;

    @BeforeEach
    void setUp() {
        reset(employeeRepository, departmentRepository);
        employeeValidator = new EmployeeValidator(employeeRepository, departmentRepository);
    }

    @Test
    void validatePostRequestBody_EmployeeIsValid_ThrowsNoException() {
        Department d1 = new Department(1L, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", d1,
                "e@w.w1", "123", 2L);

        when(departmentRepository.existsById(d1.getId())).thenReturn(true);
        when(employeeRepository.findByPhoneNumber(e1Dto.getPhoneNumber())).thenReturn(List.of());
        when(employeeRepository.findByEmail(e1Dto.getEmail())).thenReturn(List.of());

        assertThatNoException().isThrownBy(() -> employeeValidator.validatePostRequestBody(e1Dto));
    }

    @Test
    void validatePostRequestBody_EmployeeWithNotUniqueFields_ThrowsException() {
        Department d1 = new Department(1L, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", d1,
                "e@w.w1", "123", 2L);
        EmployeeDto e2Dto = new EmployeeDto(2L, "a2", "b2", d1,
                "e@w.w1", "123", 2L);
        Employee e2 = e2Dto.toEmployee();

        when(departmentRepository.existsById(d1.getId())).thenReturn(true);
        when(employeeRepository.findByPhoneNumber(e1Dto.getPhoneNumber())).thenReturn(List.of(e2));
        when(employeeRepository.findByEmail(e1Dto.getEmail())).thenReturn(List.of(e2));

        assertThatExceptionOfType(DtoDbFieldsNotValidException.class).isThrownBy(
                () -> employeeValidator.validatePostRequestBody(e1Dto)
        ).matches(e -> e.getErrors().equals(List.of(
                EmployeeValidator.EMAIL_ALREADY_EXISTS,
                EmployeeValidator.PHONE_NUMBER_ALREADY_EXISTS
        )));
    }

    @Test
    void validatePostRequestBody_EmployeeWithNullDepartment_ThrowsException() {
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", null,
                "e@w.w1", "123", 2L);

        when(employeeRepository.findByPhoneNumber(e1Dto.getPhoneNumber())).thenReturn(List.of());
        when(employeeRepository.findByEmail(e1Dto.getEmail())).thenReturn(List.of());

        assertThatExceptionOfType(DtoDbFieldsNotValidException.class).isThrownBy(
                () -> employeeValidator.validatePostRequestBody(e1Dto)
        ).matches(e -> e.getErrors().equals(List.of(
                EmployeeValidator.DEPARTMENT_NOT_EXISTS
        )));
    }

    @Test
    void validatePostRequestBody_EmployeeWithNotExistingDepartment_ThrowsException() {
        Department d1 = new Department(2L, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", d1,
                "e@w.w1", "123", 2L);

        when(departmentRepository.existsById(d1.getId())).thenReturn(false);
        when(employeeRepository.findByPhoneNumber(e1Dto.getPhoneNumber())).thenReturn(List.of());
        when(employeeRepository.findByEmail(e1Dto.getEmail())).thenReturn(List.of());

        assertThatExceptionOfType(DtoDbFieldsNotValidException.class).isThrownBy(
                () -> employeeValidator.validatePostRequestBody(e1Dto)
        ).matches(e -> e.getErrors().equals(List.of(
                EmployeeValidator.DEPARTMENT_NOT_EXISTS
        )));
    }

    @Test
    void validatePutRequestBody_EmployeeValid_ThrowsNoException() {
        long employeeId = 1L;
        Department d1 = new Department(1L, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", d1,
                "e@w.w1", "123", 2L);
        Employee e1 = e1Dto.toEmployee();

        when(departmentRepository.existsById(d1.getId())).thenReturn(true);
        when(employeeRepository.findByPhoneNumber(e1Dto.getPhoneNumber())).thenReturn(List.of(e1));
        when(employeeRepository.findByEmail(e1Dto.getEmail())).thenReturn(List.of(e1));

        assertThatNoException().isThrownBy(() -> employeeValidator.validatePutRequestBody(e1Dto, employeeId));
    }

    @Test
    void validatePutRequestBody_EmployeeWithNotUniqueField_ThrowsException() {
        long employeeId = 1L;
        Department d1 = new Department(1L, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", d1,
                "e@w.w1", "123", 2L);
        EmployeeDto e2Dto = new EmployeeDto(2L, "a2", "b2", d1,
                "e@w.w1", "123", 2L);
        Employee e1 = e1Dto.toEmployee();
        Employee e2 = e2Dto.toEmployee();

        when(departmentRepository.existsById(d1.getId())).thenReturn(true);
        when(employeeRepository.findByPhoneNumber(e1Dto.getPhoneNumber())).thenReturn(List.of(e1, e2));
        when(employeeRepository.findByEmail(e1Dto.getEmail())).thenReturn(List.of(e1, e2));

        assertThatExceptionOfType(DtoDbFieldsNotValidException.class).isThrownBy(
                () -> employeeValidator.validatePutRequestBody(e1Dto, employeeId)
        ).matches(e -> e.getErrors().equals(List.of(
                EmployeeValidator.EMAIL_ALREADY_EXISTS,
                EmployeeValidator.PHONE_NUMBER_ALREADY_EXISTS
        )));
    }

    @Test
    void validatePutRequestBody_EmployeeWithNullDepartment_ThrowsException() {
        long employeeId = 1L;
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", null,
                "e@w.w1", "123", 2L);
        Employee e1 = e1Dto.toEmployee();

        when(employeeRepository.findByPhoneNumber(e1Dto.getPhoneNumber())).thenReturn(List.of(e1));
        when(employeeRepository.findByEmail(e1Dto.getEmail())).thenReturn(List.of(e1));

        assertThatExceptionOfType(DtoDbFieldsNotValidException.class).isThrownBy(
                () -> employeeValidator.validatePutRequestBody(e1Dto, employeeId)
        ).matches(e -> e.getErrors().equals(List.of(
                EmployeeValidator.DEPARTMENT_NOT_EXISTS
        )));
    }

    @Test
    void validatePutRequestBody_EmployeeWithNotExistingDepartment_ThrowsException() {
        long employeeId = 1L;
        Department d1 = new Department(2L, "a1", "a2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1", d1,
                "e@w.w1", "123", 2L);
        Employee e1 = e1Dto.toEmployee();

        when(departmentRepository.existsById(d1.getId())).thenReturn(false);
        when(employeeRepository.findByPhoneNumber(e1Dto.getPhoneNumber())).thenReturn(List.of(e1));
        when(employeeRepository.findByEmail(e1Dto.getEmail())).thenReturn(List.of(e1));

        assertThatExceptionOfType(DtoDbFieldsNotValidException.class).isThrownBy(
                () -> employeeValidator.validatePutRequestBody(e1Dto, employeeId)
        ).matches(e -> e.getErrors().equals(List.of(
                EmployeeValidator.DEPARTMENT_NOT_EXISTS
        )));
    }
}