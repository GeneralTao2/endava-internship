package com.endava.internship.webapp.validation.dto;

import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    Long id = 0L;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    String lastName;

    Department department;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be well-formed")
    String email;

    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be blank")
    String phoneNumber;

    @Min(value = 2, message = "Salary must be greater than 1")
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
}
