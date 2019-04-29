package com.icecream.server.entity;

import org.hibernate.validator.constraints.URL;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "rss_feed")
public class RssFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL(message = "Invalid URL!") //TODO usage?
    @Size(min = 1, message = "Invalid URL!")
    @Column(length = 1000)
    private String url;

    @Size(min = 1, message = "Name must be at least 1 character!")
    private String name;

    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userEntity;

    @OneToMany(mappedBy = "rssFeedEntity", cascade = CascadeType.REMOVE)
    private List<Article> articleEntities;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }

    public List<Article> getArticleEntities() {
        return articleEntities;
    }

    public void setArticleEntities(List<Article> articleEntities) {
        this.articleEntities = articleEntities;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }
    @Override
    public String toString() {
        return "RssFeed{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", userEntity=" + userEntity +
                ", articleEntities=" + articleEntities +
                '}';
    }
}
