package com.project.my.auth.repository;

import com.project.my.auth.entity.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ErrorCodeRepository extends JpaRepository<ErrorCode, Integer> {
    // 컬럼 만 필요 해서 이렇게 반환
    @Query("SELECT e.codeNm FROM ErrorCode e WHERE e.codeId = :code")
    Optional<String> findMessageByCode(@Param("code") int code);


    @Query("SELECT e.codeId FROM ErrorCode e WHERE e.codeNm LIKE %:keyword%")
    List<Integer> findCodeIdsByCodeNmLike(@Param("keyword") String keyword);
}
