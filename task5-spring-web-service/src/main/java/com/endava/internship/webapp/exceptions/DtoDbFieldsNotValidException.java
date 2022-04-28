package com.endava.internship.webapp.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class DtoDbFieldsNotValidException extends MultiErrorDbSideException {
    public DtoDbFieldsNotValidException(List<Map.Entry<String, String>> errors) {
        super(errors);
    }
}
