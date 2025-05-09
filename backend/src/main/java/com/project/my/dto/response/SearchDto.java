package com.project.my.dto.response;


import com.project.my.entity.enums.BoardType;
import com.project.my.entity.enums.Role;

public record SearchDto(
       //날짜
        String startDate,
        String endDate,

        // 게시판 분류
        BoardType boardType,

       //검색어
        String searchType,
        String searchValue,

//        Boolean isUse,
       // 권한
       Role role
) {
}
