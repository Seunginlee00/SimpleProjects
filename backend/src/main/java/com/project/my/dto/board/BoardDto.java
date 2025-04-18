package com.project.my.dto.board;


import com.project.my.entity.board.Board;
import com.project.my.entity.board.BoardConfig;
import java.time.LocalDateTime;

public record BoardDto(
        Long boardId,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate,
        com.project.my.entity.enums.BoardType boardType,
        String classify,
        String subject,
        String content,
        int views,
        String userName,
        Boolean isDelete,
        Boolean isViewUse,
        Boolean isTopExpo
) {

    public Board toEntity(String userName, BoardConfig boardConfig){
        return Board.builder()
                .classify(classify)
                .subject(subject)
                .content(content)
                .views(0)
                .userName(userName)
                .isDelete(false)
                .isTopExpo(isTopExpo)
                .boardConfig(boardConfig)
                .build();
    }


    public BoardDto toDto(Board b){
        return new BoardDto(b.getId(),b.getCreatedDate(),b.getModifiedDate(), b.getBoardConfig().getBoardType(),
                b.getClassify(), b.getSubject(),b.getContent(), b.getViews(), b.getUserName(), b.getIsDelete(),
                b.getBoardConfig().getIsViewUse(),b.getIsTopExpo());
    }



    public BoardDto (Board b){
        this(b.getId(),b.getCreatedDate(),b.getModifiedDate(), b.getBoardConfig().getBoardType(),
                b.getClassify(), b.getSubject(),b.getContent(), b.getViews(), b.getUserName(), b.getIsDelete(),
                b.getBoardConfig().getIsViewUse(),b.getIsTopExpo());
    }


}
