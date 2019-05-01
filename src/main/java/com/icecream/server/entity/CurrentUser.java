package com.icecream.server.entity;

import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser extends  org.springframework.security.core.userdetails.User {
  private User user;

  public CurrentUser(User user) { //TODO
    super(user.getPhoneNumber(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public Long getId() {
    return user.getId();
  }

  public List<RssFeed> getRssFeeds()
  {
    List<RssFeed> registerChannels = new ArrayList<>();
    if (user != null)
    {
      user.getRssFeedEntities().forEach((f) -> registerChannels.add(f));
    }
    return registerChannels;
  }

  @Override
  public String toString() {
    return "CurrentUser{" +
            "user=" + user +
            "} " + super.toString();
  }
}