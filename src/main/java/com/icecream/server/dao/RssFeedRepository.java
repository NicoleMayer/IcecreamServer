package com.icecream.server.dao;

import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
  List<RssFeed> findByUserEntities(User user);
  List<RssFeed> findByCategory(String category);
  List<RssFeed> findByUrl(String url); //TODO future for query add new source
//  List<String>  findDistinctCategory();

}
