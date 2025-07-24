package com.project.my.auth.repository;

import com.project.my.auth.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    List<AccessLog> findByIpaddrAndAccessDateBetween(
            String ipaddr,
            LocalDateTime from,
            LocalDateTime to
    );

}

