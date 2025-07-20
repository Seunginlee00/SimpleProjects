package com.project.my.dto.board;


import com.project.my.entity.board.Board;
import com.project.my.entity.board.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Optional;

public record BoardInquiryDto(
        @Schema(description = "게시판 번호")
        Long boardId,
        @Schema(description = "분류")
        String classify,
        @Schema(description = "제목")
        String subject,
        LocalDateTime bCreatedDate,
        LocalDateTime bModfiedDate,
        String content,
        String userName,
        int views,
        Boolean isTopExpo,
        Boolean answerType,

        BoardInquiryCommentDto comment

) {

    public record BoardInquiryCommentDto(
            // 댓글
            Long commentId,
            String commentContent,
            LocalDateTime cCreatedDate,
            LocalDateTime cModfiedDate

    ){

    }

    // 댓글이 없는 경우
    public BoardInquiryDto(Board board){
        this(
                board.getId(),
                Optional.ofNullable(board.getClassify()).orElse("none"),
                Optional.ofNullable(board.getSubject()).orElse("none"),
                 board.getCreatedDate(),
                Optional.ofNullable(board.getModifiedDate()).orElse(LocalDateTime.MIN),
                Optional.ofNullable(board.getContent()).orElse("none"),
                Optional.ofNullable(board.getUsers().getNickname()).orElse("none"),
                board.getViews(),board.getIsTopExpo(),board.getAnswerType(),
                null
        );
    }

    // 댓글이 있는 경우
    public BoardInquiryDto(Board board, Comment comment){
        this(
                board.getId(),
                Optional.ofNullable(board.getClassify()).orElse("none"),
                Optional.ofNullable(board.getSubject()).orElse("none"),
                board.getCreatedDate(),
                Optional.ofNullable(board.getModifiedDate()).orElse(LocalDateTime.MIN),
                Optional.ofNullable(board.getContent()).orElse("none"),
                Optional.ofNullable(board.getUsers().getNickname()).orElse("none"),
                board.getViews(),board.getIsTopExpo(),board.getAnswerType(),
                new BoardInquiryCommentDto(
                        comment.getId(),
                        Optional.ofNullable(comment.getContent()).orElse("none"),
                        comment.getCreatedDate(),
                        Optional.ofNullable(comment.getModifiedDate()).orElse(LocalDateTime.MIN)
                )
            );
    }

}
