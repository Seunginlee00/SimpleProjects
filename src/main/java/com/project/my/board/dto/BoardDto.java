package com.project.my.board.dto;


import com.project.my.board.entity.Board;
import com.project.my.board.entity.BoardConfig;
import com.project.my.board.entity.BoardType;
import com.project.my.user.entity.Users;

import java.time.LocalDateTime;
import java.util.Optional;

public record BoardDto(
        Long boardId,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate,
        BoardType boardType, //n 공지 , f 자유
        String classify,
        String subject,
        String content,
        int views,
        Boolean isDelete,
        Boolean isViewUse,
        Boolean isTopExpo
) {

    public Board toEntity(Users users, BoardConfig boardConfig){
        return Board.builder()
                .classify(classify)
                .subject(subject)
                .content(content)
                .views(0)
                .isDelete(false)
                .isTopExpo(isTopExpo)
                .answerType(false)
                .boardConfig(boardConfig)
                .users(users)
                .build();
    }

    public BoardDto(Board b){
        this(b.getId(),
                b.getCreatedDate(),
                b.getModifiedDate(),
                b.getBoardConfig().getBoardType(),
                Optional.ofNullable(b.getClassify())
                        .orElse("none"),
                Optional.ofNullable(b.getSubject())
                        .orElse("none"),
                Optional.ofNullable(b.getContent())
                        .orElse("none"),
                b.getViews(),
                b.getIsDelete(),
                b.getBoardConfig().getIsViewUse(),
                b.getIsTopExpo()

        );
    }


}
