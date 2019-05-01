package com.icecream.server.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * This class is a data class for User.
 *
 * @author NicoleMayer
 */
@Entity
@Table(name = "user")
public class User  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<RssFeed> rssFeedEntities;

  @Column(name="auth_token")
  private String authtoken;

  public String getAuthtoken() {
    return authtoken;
  }
  public void setAuthtoken(String authtoken) {
    this.authtoken = authtoken;
  }

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

  public Set<RssFeed> getRssFeedEntities() {
    return rssFeedEntities;
  }

  public void setRssFeedEntities(Set<RssFeed> rssFeedEntities) {
    this.rssFeedEntities = rssFeedEntities;
  }

  public User() {
    super();
  }

  /**
   * This is a constructor for User class.
   * @param phoneNumber The input phone number.
   * @param username The input username.
   * @param password The input password.
   */
  public User(String phoneNumber, String username, String password) {
    super();
    this.phoneNumber = phoneNumber;
    this.username = username;
    this.password = password;
    this.rssFeedEntities = new HashSet<>();
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username=" + username +
            '}';
  }
}
