package com.endava.internship.webapp.exceptions;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
