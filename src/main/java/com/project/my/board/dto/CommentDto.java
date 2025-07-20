package com.project.my.board.dto;

import com.project.my.board.entity.Board;
import com.project.my.board.entity.Comment;
import com.project.my.user.entity.Users;

import java.time.LocalDateTime;

public record CommentDto(
        Long boardId,
        Long commentId,
        String content,
        String userName,
        LocalDateTime createDate,
        LocalDateTime modifiedDate

) {
    public Comment toEntity(Users users, Board board){
        return Comment.builder()
                .content(content)
                .users(users)
                .build();
    }
}
