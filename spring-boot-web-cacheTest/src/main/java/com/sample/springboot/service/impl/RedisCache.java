package com.sample.springboot.service.impl;

import com.sample.springboot.entities.Employee;
import com.sample.springboot.service.Cache;
import com.sample.springboot.service.LoadingFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Service
public class RedisCache implements Cache<Integer, Employee> {

  private volatile static List<String> reqArguments = new ArrayList<>();
  private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

  @Autowired
  private RedisTemplate<Object, Employee> empRedisTemplate;

  @Autowired
  private LoadingFunction loadingFunction;

  /**
   * Get Employee in cache by key
   * @param key
   */
  @Override
  public Employee get(Integer key)
  {

    String empKey = "Emp_" + key;
    Employee employee = null;
    try
    {
      //Get value from cache
      employee = empRedisTemplate.opsForValue().get(empKey);

      if(null == employee)
      {
        // No record in cache
        // Need to check if other thread is using the same parameter to search
        if(reqArguments.contains(empKey))
        {
          // Another existing thread is using the same parameter to search, then need to wait
          Thread.sleep(3000L);
          employee = get(key);
        }
        else
        {
          reqArguments.add(empKey);

          //Get data from DB
          employee = (Employee) loadingFunction.apply(key);
          if(null == employee) {
            employee = new Employee();
          }
          //Save data in cache and set timeout as 5 minutes
          empRedisTemplate.opsForValue().set(empKey,employee, 5, TimeUnit.MINUTES);
        }
      }
    }
    catch(Exception e) {}
    finally
    {
      if(reqArguments.contains(empKey))
      {
        reqArguments.remove(empKey);
      }
    }
    return employee;
  }

  /**
   * Get Employee in cache by key in multi thread situation
   * @param key
   */
  @Async("taskExecutor")
  @Override
  public CompletableFuture<Employee> getInMultiThread(Integer key) {
    logger.info(Thread.currentThread().getName() + " starts to get value by key: " + key);
    Employee employee = get(key);
    logger.info(Thread.currentThread().getName() + " finishes getting value by key: " + key);
    return CompletableFuture.completedFuture(employee);
  }
}
