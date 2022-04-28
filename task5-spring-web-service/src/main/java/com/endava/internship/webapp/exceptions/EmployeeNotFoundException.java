package com.endava.internship.webapp.exceptions;

public class EmployeeNotFoundException extends OneErrorDbSideException {
    public EmployeeNotFoundException(Long employeeId) {
        super("employee", "Employee " + employeeId + " not found");
    }
}
