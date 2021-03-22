package com.sample.springboot.repository;

import com.sample.springboot.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

  Employee getByLastName(String lastName);
}
