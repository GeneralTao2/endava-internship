package com.endava.internship.webapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Value
public class ErrorResponse {
    LocalDateTime timestamp = LocalDateTime.now();
    int status;
    Map<String, String> errors;
    String path;

    public ErrorResponse(int status, Map<String, String> errors, String path) {
        this.status = status;
        this.errors = errors;
        this.path = path;
    }
}
