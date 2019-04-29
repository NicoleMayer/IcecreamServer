package com.icecream.server.entity;

import javax.persistence.*;
import java.util.List;

/**
 * This class is a data class for Country.
 * Used for language for app.
 *
 * @author NicoleMayer
 */
@Entity
@Table(name = "country")
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "country_name", length = 100)
  private String contryName;

  @OneToMany(mappedBy = "countryEntity")
  private List<User> userEntity;

  public Long getId() { return id; }

  public void setId(Long id) { this.id = id; }

  public String getContryName() { return contryName; }

  public void setContryName(String name) { this.contryName = name; }

  public List<User> getUserEntity() { return userEntity; }

  public void setUserEntity(List<User> userEntity) { this.userEntity = userEntity; }
}

