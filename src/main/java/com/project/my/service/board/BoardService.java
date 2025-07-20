package com.project.my.service.board;

import com.project.my.common.Util;
import com.project.my.common.exception.ApiException;
import com.project.my.common.exception.ExceptionData;
import com.project.my.dto.board.*;
import com.project.my.dto.response.SearchDto;
import com.project.my.entity.board.Board;
import com.project.my.entity.board.BoardConfig;
import com.project.my.entity.board.BoardConfigRepository;
import com.project.my.entity.board.BoardRepository;
import com.project.my.entity.board.Comment;
import com.project.my.entity.board.CommentRepository;
import com.project.my.entity.board.Impl.BoardRepositoryImpl;
import com.project.my.entity.user.Users;
import com.project.my.entity.user.query.UsersRepository;
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
    private final CommentRepository commentRepository;
    private final Util util;
    private final BoardRepositoryImpl boardRepoImpl;
    private final BoardConfigRepository boardConfigRepository;

    /*
    * 게시판 설정 변경
    * */

    public String boardConfigChange(BoardConfigDto configDto){
        BoardConfig config = boardConfigRepository.findByBoardType(configDto.boardType());
        if(config == null){
            throw new ApiException(ExceptionData.NOT_FOUND_BOARD_CONFIG);
        }

        config.update(configDto);

        return "수정 되었습니다.";

    }



    /*
    *  글쓰기
    * */

    public BoardResponseDto boardInsert(Long userId, BoardDto dto) {
        Board board = null;
        BoardConfig config = configRepository.findByBoardType(dto.boardType());

        Users users = util.getCommonUserInfo(userId);

        if(config == null){
            // 해당 분류가 없다면
            BoardConfigDto cDto = new BoardConfigDto(dto.boardType(),true,100); // 기본값
            // 수정할 수 있는 api 따로 만들 예정

            config = configRepository.save(cDto.toEntity());

            board = dto.toEntity(users,config);

        }else {
            // 있다면
            board = dto.toEntity(users,config);
        }

        Board saveBoard = boardRepository.save(board);

        return new BoardResponseDto(saveBoard);
    }

    /*
    * 글수정
    * */

    public BoardResponseDto boardUpdate(BoardDto dto) {
        Board findBoard = boardRepository.findById(dto.boardId())
                        .orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_USER));

        findBoard.update(dto);

        return BoardResponseDto.from(findBoard);

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
            new ApiException(ExceptionData.NOT_FOUND_BOARD));

        board.viewUpdate();

        Comment comment = commentRepository.findByBoard(board);

        BoardInquiryDto dto = new BoardInquiryDto(board);

        if(comment != null) {
            dto = new BoardInquiryDto(board,comment);
        }

        return dto;
    }

    @Transactional(readOnly = true)
    public Object boardList(SearchDto dto, Pageable pageable) {

        return boardRepoImpl.boardList(dto,pageable)
                .map(BoardDto:: new);
    }


//    /*
//    * 댓글 입력
//    * */
//    public void commentInsert(Long userId, CommentDto dto) {
//        Board board = boardRepository.findById(dto.boardId()).orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_BOARD));
//
//        Users users = util.getCommonUserInfo(userId);
//
//        Comment comment = dto.toEntity(users,board);
//
//        commentRepository.save(comment);
//        board.setAnswerType();
//    }
//
//    /*
//    * 댓글 수정
//    * */
//
//    public void commentUpdate(CommentDto dto) {
//        Comment comment commentRepository.findByBoard(dto.boardId())
//    }

//    /*
//    * 댓글 삭제
//    * */
//
//    public void commentDelete(Long commentId) {
//        commentRepository.deleteById(commentId);
//    }
}
