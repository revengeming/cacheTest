package com.sample.springboot.controller;

import com.sample.springboot.entities.Employee;
import com.sample.springboot.service.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

  @Autowired
  Cache cache;

  /**
   * query employee by Id
   *
   * @param idStr
   * @return
   */
  @GetMapping(value = "/emp")
  public String getEmployeeById(@RequestParam(value = "id", defaultValue = "1") String idStr) {
    Integer id = 1;
    Employee employee = null;
    try
    {
      id = Integer.parseInt(idStr);
      employee = (Employee) cache.get(id);
      return employee.toString();
    }
    catch (Exception e) {}
    return null;
  }
}
