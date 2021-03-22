package com.sample.springboot;

import com.sample.springboot.entities.Employee;
import com.sample.springboot.service.Cache;
import com.sample.springboot.service.LoadingFunction;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCacheTestApplicationTests {

  @Autowired
  private Cache redisCache;

  @Autowired
  private LoadingFunction loadingFunction;

  private static List<Employee> empList;

  @Before
  public void saveAndSearchInMysql() {
    Employee employee1 = new Employee("aaa", "aaa@163.com", 0);
    Employee employee2 = new Employee("bbb", "bbb@163.com", 1);
    loadingFunction.save(employee1);
    loadingFunction.save(employee2);

    empList = loadingFunction.findAll();
  }

  @After
  public void deleteInMysql() {
    empList.stream().map(e -> e.getId()).forEach(i -> loadingFunction.delete(i));
  }

  /**
   * Test searching employee in multi thread situation
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @Test
  public void testGetInMultiThread() throws ExecutionException, InterruptedException {
    // Kick of multiple, asynchronous lookups
    //Test with the same request parameters
    CompletableFuture<Employee> emp1 = redisCache.getInMultiThread(empList.get(0).getId());
    CompletableFuture<Employee> emp2 = redisCache.getInMultiThread(empList.get(0).getId());
    //Test with the different request parameters
    CompletableFuture<Employee> emp3 = redisCache.getInMultiThread(empList.get(1).getId());

    CompletableFuture.allOf(emp1, emp2, emp3).join();
    Assert.assertEquals("aaa", emp1.get().getLastName());
    Assert.assertEquals("aaa", emp2.get().getLastName());
    Assert.assertEquals("bbb", emp3.get().getLastName());
  }

  /**
   * Test searching employee in cache
   */
  @Test
  public void testGetInCache() {
    Employee employee = (Employee) redisCache.get(empList.get(1).getId());
    Assert.assertEquals("bbb", employee.getLastName());
  }

  /**
   * Test searching employee in DB
   */
  @Test
  public void testGetInDB() {
    Employee employee = (Employee) loadingFunction.apply(empList.get(1).getId());
    Assert.assertEquals("bbb", employee.getLastName());
  }
}
