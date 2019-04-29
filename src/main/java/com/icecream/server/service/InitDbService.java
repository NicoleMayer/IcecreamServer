//package com.icecream.server.service;
//
//import com.icecream.server.dao.ArticleRepository;
//import com.icecream.server.dao.CountryRepository;
//import com.icecream.server.dao.UserRepository;
//import com.icecream.server.entity.Article;
//import com.icecream.server.entity.User;
//import com.icecream.server.exception.RSSException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import pl.dmichalski.rss.core.entity.*;
//import pl.dmichalski.rss.core.exception.RSSException;
//import pl.dmichalski.rss.core.repository.BlogRepository;
//import pl.dmichalski.rss.core.repository.CountryRepository;
//import pl.dmichalski.rss.core.repository.RoleRepository;
//import pl.dmichalski.rss.core.repository.UserRepository;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Author: Daniel
// */
//@Service
//@Transactional
//public class InitDbService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private CountryRepository countryRepository;
//
//    @Autowired
//    private RssServiceImpl rssService;
//
//    @Autowired
//    private RssFeedService rssFeedService;
//
//    @PostConstruct
//    public void init() throws RSSException {
//        if (roleRepository.findByName("ROLE_ADMIN") != null)  return;
//
//        UserRoleEntity userRole = createUserRole();
//        UserRoleEntity adminRole = createAdminRole();
//        roleRepository.save(userRole);
//        roleRepository.save(adminRole);
//
//        User admin = createAdmin();
//        List<UserRoleEntity> roles = createRoles(userRole, adminRole);
//        admin.setRoleEntities(roles);
//        admin.setCountryEntity(createAndSaveCountry());
//        userRepository.save(admin);
//
//        createBlogs(admin);
//    }
//
//    private UserRoleEntity createUserRole() {
//        UserRoleEntity userRoleEntityUser = new UserRoleEntity();
//        userRoleEntityUser.setName("ROLE_USER");
//        return userRoleEntityUser;
//    }
//
//    private UserRoleEntity createAdminRole() {
//        UserRoleEntity userRoleEntityAdmin = new UserRoleEntity();
//        userRoleEntityAdmin.setName("ROLE_ADMIN");
//        return userRoleEntityAdmin;
//    }
//
//    private List<UserRoleEntity> createRoles(UserRoleEntity userRole, UserRoleEntity adminRole) {
//        List<UserRoleEntity> roleEntities = new ArrayList<>();
//        roleEntities.add(adminRole);
//        roleEntities.add(userRole);
//        return roleEntities;
//    }
//
//    private User createAdmin() {
//        User admin = new User();
//        admin.setEnabled(true);
//        admin.setName("admin");
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        admin.setPassword(encoder.encode("admin"));
//        return admin;
//    }
//
//    private void createBlogs(User user) throws RSSException {
//        RssFeed rssFeedEntity1 = saveBlog(user, "World News", "http://feeds.skynews.com/feeds/rss/world.xml");
//        saveItems(rssFeedEntity1);
//    }
//
//    private RssFeed saveBlog(User user, String name, String url) {
//        RssFeed rssFeedEntity = new RssFeed();
//        rssFeedEntity.setName(name);
//        rssFeedEntity.setUrl(url);
//        rssFeedEntity.setUser(user);
//        rssFeedService.save(rssFeedEntity);
//        return rssFeedEntity;
//    }
//
//    private void saveItems(RssFeed rssFeedEntity) throws RSSException {
//        List<Article> itemEntities = rssService.getItems(rssFeedEntity.getUrl());
//        rssFeedEntity.setItemEntities(itemEntities);
//        blogService.saveAll(rssFeedEntity);
//    }
//
//    private CountryEntity createAndSaveCountry() {
//        CountryEntity countryEntity = new CountryEntity();
//        countryEntity.setContryName("Poland");
//        countryRepository.save(countryEntity);
//        return countryEntity;
//    }
//
//}
