package com.icecream.server.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import java.util.List;


/**
 * This class is a data class for User.
 *
 * @author NicoleMayer
 */
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "system-uuid", strategy = "uuid") //TODO WHY NEED THIS?
  private Long id;

  @Column(unique = true)
  private String phoneNumber;

  @Size(min = 3, message = "Username must be at least 3 characters!")
//  @UsernameUnique(message = "Such username already exists") TODO USE or NOT
  private String username;

  @Size(min = 5, message = "Password must be at least 5 characters!")
  private String password;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private List<RssFeed> rssFeedEntities;

  @ManyToOne
  private Country countryEntity;

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

  public List<RssFeed> getRssFeedEntities() {
    return rssFeedEntities;
  }

  public void setRssFeedEntities(List<RssFeed> rssFeedEntities) {
    this.rssFeedEntities = rssFeedEntities;
  }

  public Country getCountryEntity() { return countryEntity; }

  public void setCountryEntity(Country countryEntity) { this.countryEntity = countryEntity; }

  /**
   * This is a constructor for User class.
   * @param phoneNumber The input phone number.
   * @param username The input username.
   * @param password The input password.
   */
  public User(String phoneNumber, String username, String password) {
    this.phoneNumber = phoneNumber;
    this.username = username;
    this.password = password;
  }

  public User() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }
}
