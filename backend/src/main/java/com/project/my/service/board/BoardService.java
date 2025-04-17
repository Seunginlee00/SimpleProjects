package com.project.my.service.board;

import com.java.project.api.dto.response.SearchDto;
import com.java.project.api.dto.board.BoardDto;
import com.java.project.api.dto.board.CommentDto;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    // 입력
    void boardInsert(BoardDto dto);

    // 수정
    void boardUpdate(BoardDto dto);

    // 삭제
    void boardDelete(Long boardId);

    // 단일조회
    Object boardInquiry(Long boardId);

    // 전체조회
    Object boardList(SearchDto dto, Pageable pageable);

    // 댓글 입력
    void commentInsert(CommentDto dto);

    // 댓글 수정
    void commentUpdate(CommentDto dto);
    // 댓글 삭제
    void commentDelete(Long commentId);

}
