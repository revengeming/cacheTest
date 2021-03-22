package com.sample.springboot.service;

import com.sample.springboot.entities.Employee;

import java.util.List;

public interface LoadingFunction<K,V> {
  /**
   * Get Employee by key
   * @param key
   */
  V apply(K key);

  /**
   * Delete employee
   * @param id
   */
  public void delete(Integer id);

  /**
   * Save or update employee
   * @param employee
   */
  public void save(Employee employee);

  /**
   * Find all employees
   */
  public List<Employee> findAll();
}
