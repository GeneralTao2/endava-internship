package com.endava.internship.webapp.repository;

import com.endava.internship.webapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
