package com.icecream.server.dao;

import com.icecream.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * This class is used for database query.
 *
 * @author NicoleMayer
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * This is an interface for query the database.
   *
   * @param phoneNumber The phone number.
   * @return A instance of {@link User} with input phone number.
   */
  @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
  User findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
