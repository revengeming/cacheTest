package com.sample.springboot.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Table(name = "SAMPEL_EMPLOYEES")
@Entity
public class Employee implements Serializable {

	private Integer id;
	private String lastName;
  private String email;
  //1 male, 0 female
  private Integer gender;
  //private Department department;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birth;
  private Date updateTime;

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

//  @JoinColumn(name = "DEPARTMENT_ID")
//  @ManyToOne(fetch = FetchType.LAZY)
//  public Department getDepartment() {
//    return department;
//  }
//
//  public void setDepartment(Department department) {
//    this.department = department;
//  }

  @Temporal(TemporalType.DATE)
  public Date getBirth() {
    return birth;
  }

  public void setBirth(Date birth) {
    this.birth = birth;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Employee(String lastName, String email, Integer gender) {
    super();
    this.lastName = lastName;
    this.email = email;
    this.gender = gender;
    this.birth = new Date();
    this.updateTime = new Date();
  }

  public Employee(Integer id) {
    super();
    this.id = id;
  }

  public Employee() {
  }

  @Override
  public String toString() {
    return "Employee{" +
      "id=" + id +
      ", lastName='" + lastName + '\'' +
      ", email='" + email + '\'' +
      ", gender=" + gender + '\'' +
      ", birth=" + birth + '\'' +
      ", updateTime=" + updateTime +
      '}';
  }
}
