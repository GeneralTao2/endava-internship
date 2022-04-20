package com.endava.internship.webapp.repository;

import com.endava.internship.webapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
