package com.endava.internship.webapp.exceptions;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OneErrorDbSideException extends MultiErrorDbSideException {
    public OneErrorDbSideException(String name, String error) {
        super(List.of(new AbstractMap.SimpleEntry<>(name, error)));
    }
}
