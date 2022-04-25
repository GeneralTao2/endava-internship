package com.endava.internship.webapp.controller;

import com.endava.internship.webapp.exceptions.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValidException(
            HttpServletRequest request,
            MethodArgumentNotValidException ex) {
        Set<Map.Entry<String, String>> errors = ex.getAllErrors()
                .stream()
                .map(FieldError.class::cast)
                .map(
                        (fieldError) -> new AbstractMap.SimpleEntry<>(
                                fieldError.getField(),
                                Optional.ofNullable(fieldError.getDefaultMessage())
                                        .orElse("No default message"))
                )
                .collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        errors,
                        request.getServletPath()));
    }

}
