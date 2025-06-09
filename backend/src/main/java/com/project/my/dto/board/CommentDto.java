package com.project.my.dto.board;

import com.project.my.entity.board.Board;
import com.project.my.entity.board.Comment;
import com.project.my.entity.user.Users;

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
