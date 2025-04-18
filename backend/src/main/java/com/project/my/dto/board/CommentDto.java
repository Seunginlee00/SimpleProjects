package com.project.my.dto.board;

import com.project.my.entity.board.Board;
import com.project.my.entity.board.Comment;
import java.time.LocalDateTime;

public record CommentDto(
        Long boardId,
        Long commentId,
        String content,
        String userName,
        LocalDateTime createDate,
        LocalDateTime modifiedDate

) {
    public Comment toEntity(String userName, Board board){
        return Comment.builder()
                .content(content)
                .userName(userName)
                .build();
    }
}
