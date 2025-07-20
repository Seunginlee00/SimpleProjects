package com.project.my.common.dto.response;


import com.project.my.board.entity.BoardType;
import com.project.my.user.entity.Role;

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
