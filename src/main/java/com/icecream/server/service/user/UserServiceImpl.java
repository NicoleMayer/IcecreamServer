package com.icecream.server.service.user;

import com.icecream.server.dao.ArticleRepository;
import com.icecream.server.dao.RssFeedRepository;
import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * This class is the implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private final UserRepository userRepository;

  private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void saveUser(User user) {
    LOGGER.debug("Save the new user to DB");
//    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); //TODO 不太明白这里加密的作用

    userRepository.save(user);
  }

  @Override
  public List<User> getAllUsers() {
    LOGGER.debug("Getting all users");
    return userRepository.findAll();
  }

  @Override
  public boolean check(User user, String password) {
    LOGGER.debug(String.format("Check the password %s", password));
    return user != null && user.getPassword().equals(password);
  }

  @Override
  public User findByPhoneNumber(String phoneNumber) {
    LOGGER.debug(String.format("Getting user by phone number=%s", phoneNumber));
    return userRepository.findByPhoneNumber(phoneNumber);
  }

  @Override
  public Optional<User> findById(long id) {
    LOGGER.debug(String.format("Getting user=%d", id));
    return userRepository.findById(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User getCurrentUser(){
    String phoneNumber = null;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
      phoneNumber = ((UserDetails)principal).getUsername();
    } else {
      phoneNumber = principal.toString();
    }
    return findByPhoneNumber(phoneNumber);
  }

//  @Override
//  public User findAllChannels(User user) {
//    List<RssFeed> rssFeedEntities = rssFeedRepository.findByUserEntity(user);
//    for (RssFeed rssFeedEntity : rssFeedEntities) {
//      Pageable publishedDate = PageRequest.of(0, 10, Sort.Direction.DESC, "publishedTime");
//      List<Article> articleEntities = articleRepository.findByRssFeedEntity(rssFeedEntity, publishedDate);
//      rssFeedEntity.setArticleEntities(articleEntities);
//    }
//    user.setRssFeedEntities(rssFeedEntities);
//    return user;
//  }
}
