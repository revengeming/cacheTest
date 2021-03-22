package com.sample.springboot.service.impl;

import com.sample.springboot.entities.Employee;
import com.sample.springboot.repository.EmployeeRepository;
import com.sample.springboot.service.LoadingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MysqlLoadingFunction implements LoadingFunction<Integer, Employee> {

  @Autowired
  private EmployeeRepository employeeRepository;

  /**
   * Get Employee by key
   * @param key
   */
  @Override
  @Transactional(readOnly = true)
  public Employee apply(Integer key) {
    //Get data from Mysql DB
    return employeeRepository.findOne(key);
  }

  /**
   * Delete employee
   * @param id
   */
  @Override
  @Transactional
  public void delete(Integer id) {
    employeeRepository.delete(id);
  }

  /**
   * Save or update employee
   * @param employee
   */
  @Override
  @Transactional
  public void save(Employee employee) {
    employee.setUpdateTime(new Date());
    employeeRepository.saveAndFlush(employee);
  }

  /**
   * Find all employees
   */
  @Override
  @Transactional(readOnly = true)
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }
}
