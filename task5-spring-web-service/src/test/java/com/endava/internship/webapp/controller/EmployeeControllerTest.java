package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.exceptions.EmployeeNotFoundException;
import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.EmployeeRepository;
import com.endava.internship.webapp.service.DepartmentService;
import com.endava.internship.webapp.service.EmployeeService;
import com.endava.internship.webapp.validation.dto.EmployeeDto;
import com.endava.internship.webapp.validation.validators.db.EmployeeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        //TODO: doesnt work
        //verify(employeeValidator).validatePostRequestBody(e1Dto);
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
    @Disabled
    void newEmployee_addBadUniqueFieldEmployee_returnErrorResponse() throws Exception {
        Department d1 = new Department(1L, "f1", "f2");
        EmployeeDto e1Dto = new EmployeeDto(1L, "a1", "b1",
                d1, "f@f.f", "123", 1L);
        String e1DtoJson = mapper.writeValueAsString(e1Dto);
        String path = "/employees";
        int status = HttpStatus.BAD_REQUEST.value();

        //TODO: Can I even do this? (doest work anyway)
        /*
        doThrow(new MethodArgumentNotValidException(new MethodParameter(getClass().getMethod("blankMethod"), -1),
                new BeanPropertyBindingResult(null, "Test") {{
                    Stream.of(
                                    EmployeeValidator.PHONE_NUMBER_ALREADY_EXISTS,
                                    EmployeeValidator.EMAIL_ALREADY_EXISTS
                            )
                            .map(errorEntry -> new FieldError(
                                    "null",
                                    errorEntry.getKey(),
                                    errorEntry.getValue()))
                            .forEach(this::addError);
                }}
        )).when(employeeValidator).validatePostRequestBody(e1Dto);*/

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(e1DtoJson))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(EmployeeValidator.PHONE_NUMBER_ALREADY_EXISTS.getValue())))
                .andExpect(content().string(containsString(EmployeeValidator.EMAIL_ALREADY_EXISTS.getValue())))
                .andExpect(jsonPath("$.errors", hasSize(2)));
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
}