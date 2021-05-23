package com.ys.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user.id FROM User user WHERE user.email = :email")
    Long findByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE User user SET user.token = :token WHERE user.id = :userId")
    int updateToken(@Param("userId") Long userId, @Param("token") String token);

}
