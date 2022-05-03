package com.endava.internship.webapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class MultiErrorDbSideException extends RuntimeException {
    private final List<Map.Entry<String, String>> errors;
}
