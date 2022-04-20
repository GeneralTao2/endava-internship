package com.endava.internship.webapp.model;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    long id;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @ManyToOne
    Department department;
    @UniqueElements
    @NotBlank
    String email;
    @UniqueElements
    @NotBlank
    String phoneNumber;
    @NotNull
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
