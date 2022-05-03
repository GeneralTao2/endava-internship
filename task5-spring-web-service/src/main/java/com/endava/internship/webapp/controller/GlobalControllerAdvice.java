package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.exceptions.DepartmentNotFoundException;
import com.endava.internship.webapp.exceptions.DtoDbFieldsNotValidException;
import com.endava.internship.webapp.exceptions.EmployeeNotFoundException;
import com.endava.internship.webapp.validation.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResponse handleMethodArgumentNotValidException(
            HttpServletRequest request,
            MethodArgumentNotValidException ex) {
        List<Map.Entry<String, String>> errors = ex.getAllErrors()
                .stream()
                .map(FieldError.class::cast)
                .map(
                        (fieldError) -> new AbstractMap.SimpleEntry<>(
                                fieldError.getField(),
                                Optional.ofNullable(fieldError.getDefaultMessage())
                                        .orElse("No default message"))
                )
                .collect(Collectors.toList());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                errors,
                request.getServletPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DtoDbFieldsNotValidException.class)
    protected ErrorResponse employeeNotFoundHandler(
            HttpServletRequest request,
            DtoDbFieldsNotValidException ex
    ) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrors(),
                request.getServletPath()
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DepartmentNotFoundException.class)
    protected ErrorResponse departmentNotFoundHandler(
            HttpServletRequest request,
            DepartmentNotFoundException ex
    ) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getErrors(),
                request.getServletPath()
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ErrorResponse employeeNotFoundHandler(
            HttpServletRequest request,
            EmployeeNotFoundException ex
    ) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getErrors(),
                request.getServletPath()
        );
    }


}
