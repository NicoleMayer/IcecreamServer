package com.icecream.server.dao;

import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is used for database query.
 *
 * @author NicoleMayer
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByPhoneNumber(String phoneNumber);
}
