package com.project.my.dto.board;


import com.project.my.entity.board.Board;
import com.project.my.entity.board.BoardConfig;
import com.project.my.entity.enums.BoardType;
import com.project.my.entity.user.Users;

import java.time.LocalDateTime;
import java.util.Optional;

public record BoardDto(
        Long boardId,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate,
        BoardType boardType,
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
