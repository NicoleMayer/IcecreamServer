package com.icecream.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * @description This class is a data class for User
 */
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private Long id;

  @Column(unique = true)
  private String phoneNumber;

  private String username;

  private String password;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User(String phoneNumber, String username, String password) {
    this.phoneNumber = phoneNumber;
    this.username = username;
    this.password = password;
  }

  public User() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }
}
