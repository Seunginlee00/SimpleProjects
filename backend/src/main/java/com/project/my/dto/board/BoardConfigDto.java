package com.project.my.dto.board;


import com.project.my.entity.board.BoardConfig;
import com.project.my.entity.enums.BoardType;

public record BoardConfigDto(
        BoardType boardType,
        Boolean isViewUse,
        int topExpoCount
) {
    public BoardConfig toEntity(){
        return BoardConfig.builder()
                .boardType(boardType())
                .isViewUse(isViewUse()) // 기본값
                .topExpoCount(topExpoCount()) //기본값
                .build();
    }
}
