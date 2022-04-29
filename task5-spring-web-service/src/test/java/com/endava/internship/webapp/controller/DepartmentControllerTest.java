package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.validation.dto.DepartmentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentRepository departmentRepository;

    ObjectMapper mapper;


    @BeforeEach
    void setUp() {
        reset(departmentRepository);
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        //Mockito.when(METHOD_EXPECTED_TO_BE_CALLED).thenReturn(AnyObjectoftheReturnType);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void all_HaveSeveralDepartment_ReturnThemAsList() throws Exception {
        Department d1 = new Department(1L, "a1", "b1");
        Department d2 = new Department(2L, "a2", "b2");
        Department d3 = new Department(3L, "a3", "b3");
        List<Department> l1 = List.of(d1, d2, d3);
        String l1Json = mapper.writeValueAsString(l1);

        when(departmentRepository.findAll()).thenReturn(l1);

        mockMvc.perform(get("/departments"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(l1Json));
    }

    @Test
    void all_HaveNoDepartment_ReturnEmptyList() throws Exception {
        List<Department> l1 = List.of();
        String l1Json = mapper.writeValueAsString(l1);

        when(departmentRepository.findAll()).thenReturn(l1);

        mockMvc.perform(get("/departments"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(l1Json));
    }

    @Test
    void one_HaveDepartment_ReturnIt() throws Exception {
        long d1Id = 1L;
        Department d1 = new Department(d1Id, "a1", "b1");
        Optional<Department> optionalD1 = Optional.of(d1);
        String d1Json = mapper.writeValueAsString(d1);

        when(departmentRepository.findById(d1Id)).thenReturn(optionalD1);

        mockMvc.perform(get("/departments/" + d1Id))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(d1Json));
    }

    @Test
    void one_NoDepartment_ReturnErrorResponse() throws Exception {
        long d1Id = 1L;
        int status = HttpStatus.NOT_FOUND.value();

        when(departmentRepository.findById(d1Id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/departments/" + d1Id))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(jsonPath("$.errors[0].department", containsString(String.valueOf(d1Id))));
    }

    @Test
    void newDepartment_SaveGoodDepartment_ReturnThatDepartment()
            throws Exception {
        DepartmentDto d1Dto = new DepartmentDto(1L, "a1", "b1");
        Department d1 = d1Dto.toDepartment();
        String d1DtoJson = mapper.writeValueAsString(d1Dto);
        String d1Json = mapper.writeValueAsString(d1);

        when(departmentRepository.save(d1)).thenReturn(d1);

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(d1DtoJson))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(d1Json));
    }

    //TODO: https://www.baeldung.com/parameterized-tests-junit-5
    @Test
    void newDepartment_SaveDepartmentWithNullField_ReturnErrorResponse()
            throws Exception {
        Department d1 = new Department(1L, null, null);
        String d1Json = mapper.writeValueAsString(d1);
        String path = "/departments";
        int status = HttpStatus.BAD_REQUEST.value();
        List<Map.Entry<String, String>> errors = List.of(
                new AbstractMap.SimpleEntry<>("location", DepartmentDto.LOCATION_NULL_MESSAGE),
                new AbstractMap.SimpleEntry<>("name", DepartmentDto.NAME_NULL_MESSAGE)
        );
        String errorJson = mapper.writeValueAsString(errors);

        when(departmentRepository.save(d1)).thenReturn(d1);

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(d1Json))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(errorJson)));
    }

    @Test
    void newDepartment_SaveDepartmentWithBlankField_ReturnErrorResponse()
            throws Exception {
        Department d1 = new Department(1L, "", "");
        String d1Json = mapper.writeValueAsString(d1);
        String path = "/departments";
        int status = HttpStatus.BAD_REQUEST.value();
        List<Map.Entry<String, String>> errors = List.of(
                new AbstractMap.SimpleEntry<>("location", DepartmentDto.LOCATION_BLANK_MESSAGE),
                new AbstractMap.SimpleEntry<>("name", DepartmentDto.NAME_BLANK_MESSAGE)
        );
        String errorJson = mapper.writeValueAsString(errors);

        when(departmentRepository.save(d1)).thenReturn(d1);

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(d1Json))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status", equalTo(status)))
                .andExpect(content().string(containsString(errorJson)));
    }

    @Test
    void replaceDepartment_ReplaceExistingDepartmentWithGoodDepartment_ReturnIt() throws Exception {
        long d1Id = 1L;
        Department d1 = new Department(d1Id, "a1", "b1");
        String d1Json = mapper.writeValueAsString(d1);

        when(departmentRepository.save(d1)).thenReturn(d1);

        mockMvc.perform(post("/departments/"+d1Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(d1Json))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(d1Json));
    }
}