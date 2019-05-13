package com.icecream.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is a data class for rss feed.
 *
 * @author NicoleMayer
 */
@Entity
@Table(name = "rss_feed")
public class RssFeed implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private Long id;

  @Column(unique = true)
  private String url;

  @Column(name = "channel_name", unique = true)
  private String channelName;

  private String category;

  @JsonIgnore
  @ManyToMany(mappedBy = "rssFeedEntities", fetch = FetchType.EAGER)
  private Set<User> userEntities;

  @JsonIgnore
  @OneToMany(mappedBy = "rssFeedEntity", cascade = CascadeType.REMOVE)
  private List<Article> articleEntities;

  /**
   * This is a constructor for RssFeed class.
   */
  @JsonIgnore
  public RssFeed() {
    super();
  }

  /**
   * This is a constructor for RssFeed class.
   *
   * @param url The input url.
   */
  public RssFeed(String url) {
    super();
    this.url = url;
  }

  /**
   * This is a constructor for RssFeed class.
   *
   * @param user The input user object.
   * @param url  The input url.
   */
  @JsonIgnore
  public RssFeed(User user, String url) {
    this.url = url;
    if (userEntities == null) {
      userEntities = new HashSet<>();
    }
    userEntities.add(user);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Set<User> getUserEntities() {
    return userEntities;
  }

  public void setUserEntities(Set<User> userEntities) {
    this.userEntities = userEntities;
  }

  public List<Article> getArticleEntities() {
    return articleEntities;
  }

  public void setArticleEntities(List<Article> articleEntities) {
    this.articleEntities = articleEntities;
  }

  /**
   * String representation of the rss feed.
   *
   * @return String stands for the rss feed.
   */
  @Override
  public String toString() {
    return "RssFeed{"
            + "id=" + id
            + ", url='" + url + '\''
            + ", channelName='" + channelName + '\''
            + ", category='" + category + '\''
            + '}';
  }
}
