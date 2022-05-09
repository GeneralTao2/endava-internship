package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.exceptions.DtoDbFieldsNotValidException;
import com.endava.internship.webapp.exceptions.EmployeeNotFoundException;
import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.service.DepartmentService;
import com.endava.internship.webapp.service.EmployeeService;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import com.endava.internship.webapp.validation.dto.ErrorResponse;
import com.endava.internship.webapp.validation.validators.db.EmployeeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    EmployeeValidator employeeValidator;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        reset(employeeRepository);
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    void blankMethod() {

    }

    @Test
    void all_ThereAreSeveralEmployees_ReturnThem() throws Exception {
        Department d1 = new Department(1L, "f1", "f2");
        Employee e1 = new Employee(1L, "a1", "b1",
                d1, "e@a1.a", "pn1", 2L);
        Employee e2 = new Employee(2L, "a2", "b2",
                d1, "e@a2.a", "pn2", 2L);
        Employee e3 = new Employee(3L, "a3", "b3",
                d1, "e@a3.a", "pn3", 2L);
        List<Employee> l1 = List.of(e1, e2, e3);
        String l1Json = mapper.writeValueAsString(l1);

        when(employeeService.getAll()).thenReturn(l1);

        mockMvc.perform(get("/employees"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(l1Json));
    }

    @Test
    void all_ThereAreNoEmployees_ReturnEmptyList() throws Exception {
        List<Employee> l1 = List.of();
        String l1Json = mapper.writeValueAsString(l1);

        when(employeeService.getAll()).thenReturn(l1);

        mockMvc.perform(get("/employees"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(l1Json));
    }

    @Test
    void one_ThereIsOneEmployee_ReturnIt() throws Exception {
        long e1Id = 1L;
        Department d1 = new Department(1L, "f1", "f2");
        Employee e1 = new Employee(e1Id, "a1", "b1",
                d1, "e@a1.a", "pn1", 2L);
        String e1Json = mapper.writeValueAsString(e1);
        String path = "/employees/" + e1Id;

        when(employeeService.getOne(e1Id)).thenReturn(e1);

        mockMvc.perform(get(path))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(e1Json));
    }

    @Test
    void one_ThereIsNoEmployeeWithSuchId_ReturnErrorResponse() throws Exception {
        long notExistingId = 2L;
        String path = "/employees/" + notExistingId;
        int status = HttpStatus.NOT_FOUND.value();

        when(employeeService.getOne(notExistingId))
                .thenThrow(new EmployeeNotFoundException(notExistingId));

        mockMvc.perform(get(path))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(String.valueOf(notExistingId))))
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    @Test
    void newEmployee_addGoodEmployee_returnIt() throws Exception {
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1",
                d1, "e@a1.a", "pn1", 2L);
        Employee e1 = e1Dto.toEmployee();
        String e1Json = mapper.writeValueAsString(e1);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees";

        when(employeeService.setOne(e1Dto)).thenReturn(e1);

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(e1Json));
    }

    @Test
    void newEmployee_addNullFiledEmployee_returnErrorResponse() throws Exception {
        EmployeeDto e1Dto = new EmployeeDto(1L, null, null,
                null, null, null, null);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees";
        int status = HttpStatus.BAD_REQUEST.value();

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(EmployeeDto.FIRST_NAME_NULL_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.LAST_NAME_NULL_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.EMAIL_NULL_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.PHONE_NUMBER_NULL_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.SALARY_NULL_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(5)));
    }

    @Test
    void newEmployee_addBlankFiledEmployee_returnErrorResponse() throws Exception {
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "", "",
                d1, "", "", 2L);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees";
        int status = HttpStatus.BAD_REQUEST.value();

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(EmployeeDto.FIRST_NAME_BLANK_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.LAST_NAME_BLANK_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.PHONE_NUMBER_BLANK_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.EMAIL_BLANK_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(4)));
    }

    @Test
    void newEmployee_addBadSalaryEmployee_returnErrorResponse() throws Exception {
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1",
                d1, "f@f.f", "123", 1L);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees";
        int status = HttpStatus.BAD_REQUEST.value();

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(EmployeeDto.SALARY_LESS_THEN_2_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    @Test
    void newEmployee_addBadEmployeeWithUniqueField_returnErrorResponse() throws Exception {
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1",
                d1, "f@f.f", "123", 2L);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees";
        int status = HttpStatus.BAD_REQUEST.value();

        when(employeeService.setOne(e1Dto)).thenThrow(new DtoDbFieldsNotValidException(List.of(
                EmployeeValidator.PHONE_NUMBER_ALREADY_EXISTS,
                EmployeeValidator.EMAIL_ALREADY_EXISTS
        )));


        MockHttpServletResponse response = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(e1DtoJson))
                .andReturn()
                .getResponse();

        String body = response.getContentAsString();
        ErrorResponse errorResponse = mapper.readValue(body, ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(status);
        assertThat(errorResponse.getErrors()).containsAll(Set.of(
                        EmployeeValidator.PHONE_NUMBER_ALREADY_EXISTS,
                        EmployeeValidator.EMAIL_ALREADY_EXISTS
                        ))
                .hasSize(2);
    }

    @Test
    void newEmployee_addBadEmployeeWithNullDepartmentField_returnErrorResponse() throws Exception {
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1",
                null, "f@f.f", "123", 2L);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees";
        int status = HttpStatus.BAD_REQUEST.value();

        when(employeeService.setOne(e1Dto)).thenThrow(new DtoDbFieldsNotValidException(List.of(
                EmployeeValidator.DEPARTMENT_NOT_EXISTS
        )));


        MockHttpServletResponse response = mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andReturn()
                .getResponse();

        String body = response.getContentAsString();
        ErrorResponse errorResponse = mapper.readValue(body, ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(status);
        assertThat(errorResponse.getErrors()).containsAll(Set.of(
                        EmployeeValidator.DEPARTMENT_NOT_EXISTS
                ))
                .hasSize(1);
    }

    @Test
    void replaceEmployee_GoodExistingEmployee_ReturnIt() throws Exception {
        long e1Id = 1L;
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(e1Id, "a1", "b1",
                d1, "e@a1.a", "pn1", 2L);
        Employee e1 = e1Dto.toEmployee();
        String e1Json = mapper.writeValueAsString(e1);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees/" + e1Id;

        when(employeeService.replaceOne(e1Dto, e1Id)).thenReturn(e1);

        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(e1Json));
    }

    @Test
    void replaceEmployee_GoodNotExistingEmployee_ReturnIt() throws Exception {
        long e1Id = 2L;
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1",
                d1, "e@a1.a", "pn1", 2L);
        Employee e1 = e1Dto.toEmployee();
        String e1Json = mapper.writeValueAsString(e1);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees/" + e1Id;

        when(employeeService.replaceOne(e1Dto, e1Id)).thenReturn(e1);

        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(e1Json));
    }

    @Test
    void replaceEmployee_ExistingNullFieldEmployee_ReturnIt() throws Exception {
        long e1Id = 1L;
        EmployeeDto e1Dto = new EmployeeDto(1L, null, null,
                null, null, null, null);
        Employee e1 = e1Dto.toEmployee();
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees/" + e1Id;
        int status = HttpStatus.BAD_REQUEST.value();

        when(employeeService.replaceOne(e1Dto, e1Id)).thenReturn(e1);

        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(EmployeeDto.FIRST_NAME_NULL_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.LAST_NAME_NULL_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.EMAIL_NULL_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.PHONE_NUMBER_NULL_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.SALARY_NULL_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(5)));
    }

    @Test
    void replaceEmployee_ExistingBlankFieldEmployee_ReturnIt() throws Exception {
        long e1Id = 1L;
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "", "",
                d1, "", "", 2L);
        Employee e1 = e1Dto.toEmployee();
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees/" + e1Id;
        int status = HttpStatus.BAD_REQUEST.value();

        when(employeeService.replaceOne(e1Dto, e1Id)).thenReturn(e1);

        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(EmployeeDto.FIRST_NAME_BLANK_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.LAST_NAME_BLANK_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.PHONE_NUMBER_BLANK_MESSAGE)))
                .andExpect(content().string(containsString(EmployeeDto.EMAIL_BLANK_MESSAGE)))
                .andExpect(jsonPath("$.errors", hasSize(4)));
    }

    @Test
    void replaceEmployee_BadEmployeeWithUniqueField_returnErrorResponse() throws Exception {
        long e1Id = 1L;
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1",
                d1, "f@f.f", "123", 2L);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees/" + e1Id;
        int status = HttpStatus.BAD_REQUEST.value();

        when(employeeService.replaceOne(e1Dto, e1Id)).thenThrow(new DtoDbFieldsNotValidException(List.of(
                EmployeeValidator.PHONE_NUMBER_ALREADY_EXISTS,
                EmployeeValidator.EMAIL_ALREADY_EXISTS
        )));


        MockHttpServletResponse response = mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andReturn()
                .getResponse();

        String body = response.getContentAsString();
        ErrorResponse errorResponse = mapper.readValue(body, ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(status);
        assertThat(errorResponse.getErrors()).containsAll(Set.of(
                        EmployeeValidator.PHONE_NUMBER_ALREADY_EXISTS,
                        EmployeeValidator.EMAIL_ALREADY_EXISTS
                ))
                .hasSize(2);
    }

    @Test
    void replaceEmployee_BadEmployeeNullDepartmentField_returnErrorResponse() throws Exception {
        long e1Id = 1L;
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1",
                d1, "f@f.f", "123", 2L);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees/" + e1Id;
        int status = HttpStatus.BAD_REQUEST.value();

        when(employeeService.replaceOne(e1Dto, e1Id)).thenThrow(new DtoDbFieldsNotValidException(List.of(
                EmployeeValidator.DEPARTMENT_NOT_EXISTS
        )));


        MockHttpServletResponse response = mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andReturn()
                .getResponse();

        String body = response.getContentAsString();
        ErrorResponse errorResponse = mapper.readValue(body, ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(status);
        assertThat(errorResponse.getErrors()).containsAll(Set.of(
                        EmployeeValidator.DEPARTMENT_NOT_EXISTS
                ))
                .hasSize(1);
    }
}