package com.project.my.service.board;

import com.project.my.common.exception.NotFoundElementException;
import com.project.my.dto.board.BoardConfigDto;
import com.project.my.dto.board.BoardDto;
import com.project.my.dto.board.BoardInquiryDto;
import com.project.my.dto.board.CommentDto;
import com.project.my.dto.response.SearchDto;
import com.project.my.entity.board.Board;
import com.project.my.entity.board.BoardConfig;
import com.project.my.entity.board.BoardConfigRepository;
import com.project.my.entity.board.BoardRepository;
import com.project.my.entity.board.Comment;
import com.project.my.entity.board.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class BoardService{

    private final BoardConfigRepository configRepository;
    private final BoardRepository boardRepository;
//    private final BoardRepositoryImpl boardImpl;
    private final CommentRepository commentRepository;

    /*
    *  글쓰기
    * */

    public void boardInsert(BoardDto dto) {
        Board board = null;
        BoardConfig config = configRepository.findByBoardType(dto.boardType());

        if(config == null){
            // 해당 분류가 없다면
            BoardConfigDto cDto = new BoardConfigDto(dto.boardType(),true,100);

            config = configRepository.save(cDto.toEntity());
            board = dto.toEntity("jwt추출",config);

        }else {
            // 있다면
            board = dto.toEntity("jwt추출",config);
        }

        boardRepository.save(board);
    }

    /*
    * 글수정
    * */

    public void boardUpdate(BoardDto dto) {
        Board board =  boardRepository.findById(dto.boardId()).orElseThrow(() ->
            new NotFoundElementException("해당 게시글을 찾을 수 없습니다."));
    // 수정
        board.update(dto);

    }

    /*
    * 글 삭제
    * */
    public void boardDelete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    /*
    * 글 단일 조회
    * */
    @Transactional(readOnly = true)
    public Object boardInquiry(Long boardId) {
        Board board = boardRepository.findByIdAndIsDelete(boardId,false).orElseThrow(() ->
            new NotFoundElementException("해당 게시글을 찾을 수 없습니다."));
        board.viewUpdate();

        Comment comment = commentRepository.findByBoard(board);

        BoardInquiryDto dto = BoardInquiryDto.dto(board);

        if(comment != null) {
            dto = BoardInquiryDto.dto(board,comment);
        }

        return dto;
    }

    @Transactional(readOnly = true)
    public Object boardList(SearchDto dto, Pageable pageable) {

      return null;
//        return boardImpl.boardList(dto,pageable);
    }


    /*
    * 댓글 입력
    * */
    public void commentInsert(CommentDto dto) {
        Board board = boardRepository.findById(dto.boardId()).orElseThrow(() ->
            new NotFoundElementException("해당 게시글을 찾을 수 없습니다."));
        Comment comment = dto.toEntity("추후토큰값",board);

        commentRepository.save(comment);
        board.setAnswerType();
    }

    /*
    * 댓글 수정
    * */

    public void commentUpdate(CommentDto dto) {
    }

    /*
    * 댓글 삭제
    * */

    public void commentDelete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
