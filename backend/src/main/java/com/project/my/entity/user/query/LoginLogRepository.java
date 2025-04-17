package com.project.my.entity.user.query;

import com.java.project.api.entity.user.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog,Long> {

}