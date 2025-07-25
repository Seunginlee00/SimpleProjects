package com.project.my.board.dto;

import com.project.my.board.entity.Board;

import java.time.LocalDateTime;

public record BoardResponseDto(
        Long boardId,
        String subject,
        String content,
        String userName,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {

    public BoardResponseDto(Board board){
        this(board.getId(),
                board.getSubject(),
                board.getContent(),
                board.getUsers().getUserNm(),
                board.getCreatedDate(),
                board.getModifiedDate());
    }

    public static BoardResponseDto from(Board board){
            return new BoardResponseDto(
                    board.getId(),
                    board.getSubject(),
                    board.getContent(),
                    board.getUsers().getUserNm(),
                    board.getCreatedDate(),
                    board.getModifiedDate()
            );
    }
}
