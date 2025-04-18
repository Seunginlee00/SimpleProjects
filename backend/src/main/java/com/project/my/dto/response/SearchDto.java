package com.project.my.dto.response;


import com.project.my.entity.enums.Role;

public record SearchDto(
       //날짜
        String startDate,
        String endDate,

       //검색어
        String searchType,
        String searchValue,

//        Boolean isUse,
       // 권한
       Role role
) {
}
