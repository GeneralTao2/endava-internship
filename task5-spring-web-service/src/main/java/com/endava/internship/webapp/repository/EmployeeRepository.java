package com.endava.internship.webapp.repository;

import com.endava.internship.webapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByPhoneNumber(@NotBlank String phoneNumber);

    List<Employee> findByEmail(@NotBlank String email);
}
