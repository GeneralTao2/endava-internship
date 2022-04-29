package com.endava.internship.webapp.validation.dto;

import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.validation.constraints.ClearNotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private static final String FIRST_NAME_NULL_MESSAGE = "First name cannot be null";
    private static final String FIRST_NAME_BLANK_MESSAGE = "First name cannot be blank";
    private static final String LAST_NAME_NULL_MESSAGE = "Last name cannot be null";
    private static final String LAST_NAME_BLANK_MESSAGE = "Last name cannot be blank";
    private static final String EMAIL_NULL_MESSAGE = "Email cannot be null";
    private static final String EMAIL_NOT_BLANK = "Email cannot be blank";
    private static final String EMAIL_NOT_WELL_FORMED = "Email must be well-formed";
    private static final String PHONE_NUMBER_NOT_NULL = "Phone number cannot be null";
    private static final String PHONE_NUMBER_NOT_BLANK = "Phone number cannot be blank";
    private static final String SALARY_LESS_THEN_2 = "Salary must be greater than 1";
    private static final String SALARY_NULL = "Salary cannot be null";
    Long id = 0L;

    @NotNull(message = FIRST_NAME_NULL_MESSAGE)
    @ClearNotBlank(message = FIRST_NAME_BLANK_MESSAGE)
    String firstName;

    @NotNull(message = LAST_NAME_NULL_MESSAGE)
    @ClearNotBlank(message = LAST_NAME_BLANK_MESSAGE)
    String lastName;

    Department department;

    @NotNull(message = EMAIL_NULL_MESSAGE)
    @ClearNotBlank(message = EMAIL_NOT_BLANK)
    @Email(message = EMAIL_NOT_WELL_FORMED)
    String email;

    @NotNull(message = PHONE_NUMBER_NOT_NULL)
    @ClearNotBlank(message = PHONE_NUMBER_NOT_BLANK)
    String phoneNumber;

    @NotNull(message = SALARY_NULL)
    @Min(value = 2, message = SALARY_LESS_THEN_2)
    long salary;

    public Employee toEmployee() {
        return Employee.builder()
                .id(getId())
                .firstName(getFirstName())
                .lastName(getLastName())
                .department(getDepartment())
                .email(getEmail())
                .phoneNumber(getPhoneNumber())
                .salary(getSalary())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto employee = (EmployeeDto) o;
        return id == employee.id && salary == employee.salary && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && department.equals(employee.department) && email.equals(employee.email) && phoneNumber.equals(employee.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phoneNumber);
    }
}
