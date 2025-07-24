package com.project.my.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterCondition {
    private String where;
    private String keyword;
    private String cat1;

    private String sortField = "uid"; // 기본 정렬 필드
    private boolean asc = false;      // 기본 내림차순
}
