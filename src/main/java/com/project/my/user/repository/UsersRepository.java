package com.project.my.user.repository;

import com.project.my.user.entity.Users;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByLoginId(String loginId);
    Boolean existsByLoginId(String loginId);

    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.roleList WHERE u.loginId = :loginId")
    Users getWithRoles(@Param("loginId") String loginId);


}
