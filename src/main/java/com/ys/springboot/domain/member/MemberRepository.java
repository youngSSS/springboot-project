package com.ys.springboot.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.userId = :userId")
    Member findByUserId(@Param("userId") String userId);

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Member findByEmail(@Param("email") String email);
}
