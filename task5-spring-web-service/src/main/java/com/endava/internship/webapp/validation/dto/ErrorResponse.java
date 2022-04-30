package com.endava.internship.webapp.validation.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Value
public class ErrorResponse {
    LocalDateTime timestamp = LocalDateTime.now();
    int status;
    List<Map.Entry<String, String>> errors;
    String path;

    public ErrorResponse(int status, List<Map.Entry<String, String>> errors, String path) {
        this.status = status;
        this.errors = errors;
        this.path = path;
    }
}
