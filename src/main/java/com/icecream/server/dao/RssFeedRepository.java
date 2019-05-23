package com.icecream.server.dao;

import com.icecream.server.entity.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is used for database query.
 *
 * @author NicoleMayer
 */
public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {

  RssFeed findByUrl(String url);

  Optional<RssFeed> findById(Long id);

  List<RssFeed> findAll();
}
