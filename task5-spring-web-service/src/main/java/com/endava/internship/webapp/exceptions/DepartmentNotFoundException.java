package com.endava.internship.webapp.exceptions;

public class DepartmentNotFoundException extends OneErrorDbSideException {
    public DepartmentNotFoundException(Long departmentId) {
        super("department", "Department " + departmentId + " not found");
    }
}
