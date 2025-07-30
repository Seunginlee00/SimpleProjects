package com.project.my.common.dto.response;


import com.project.my.board.entity.BoardType;

public record SearchDto(
       //날짜
        String startDate,
        String endDate,

        // 게시판 분류
        BoardType boardType,

       //검색어
        String searchType,
        String searchValue

) {
}
