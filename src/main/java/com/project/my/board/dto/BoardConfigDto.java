package com.project.my.board.dto;


import com.project.my.board.entity.BoardConfig;
import com.project.my.board.entity.BoardType;

public record BoardConfigDto(
        BoardType boardType,
        Boolean isViewUse,
        Integer topExpoCount
) {
    public BoardConfig toEntity(){
        return BoardConfig.builder()
                .boardType(boardType())
                .isViewUse(isViewUse()) // 기본값
                .topExpoCount(topExpoCount()) //기본값
                .build();
    }
}
