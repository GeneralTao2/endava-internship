package com.endava.internship.webapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue
    long id;

    @NotNull
    @NotBlank
    String firstName;

    @NotNull
    @NotBlank
    String lastName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    Department department;

    @NotNull
    @NotBlank
    @Email
    @Column(unique = true)
    String email;

    @NotNull
    @NotBlank
    @Column(unique = true)
    String phoneNumber;

    @Column(columnDefinition = "number CHECK (salary > 1)")
    long salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && salary == employee.salary && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && department.equals(employee.department) && email.equals(employee.email) && phoneNumber.equals(employee.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phoneNumber);
    }

}
