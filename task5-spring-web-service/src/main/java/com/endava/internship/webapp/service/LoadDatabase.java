package com.endava.internship.webapp.service;

import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.model.Employee;
import com.endava.internship.webapp.repository.DepartmentRepository;
import com.endava.internship.webapp.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository,
                                   DepartmentRepository departmentRepository) {
        return args -> {
            employeeRepository.deleteAll();
            departmentRepository.deleteAll();
            Department d1 = new Department(0, "a1", "b1");
            Department d2 = new Department(0, "a2", "b2");
            Department d3 = new Department(0, "a3", "b3");
            Department d4 = new Department(0, "a4", "b4");

            Employee e1 = new Employee(0, "a1", "b1", null, "e1@g.com", "1", 5);
            Employee e2 = new Employee(0, "a2", "b2", null, "e2@g.com", "2", 2);
            Employee e3 = new Employee(0, "a3", "b3", null, "e3@g.com", "3", 3);
            Employee e4 = new Employee(0, "a4", "b4", null, "e4@g.com", "4", 4);

            employeeRepository.save(e1);
            employeeRepository.save(e2);
            employeeRepository.save(e3);
            employeeRepository.save(e4);

            departmentRepository.save(d1);
            departmentRepository.save(d2);
            departmentRepository.save(d3);
            departmentRepository.save(d4);


            departmentRepository.save(d1);
            departmentRepository.save(d2);
        };
    }

}
