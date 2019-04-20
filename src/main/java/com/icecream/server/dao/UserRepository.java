package com.icecream.server.dao;

import com.icecream.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @description This class is used for database query
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * @description: This is an interface for query the database
   * @param
   * @return
   * @author NicoleMayer
   * @date 2019-04-20
   */
  @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
  User findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
