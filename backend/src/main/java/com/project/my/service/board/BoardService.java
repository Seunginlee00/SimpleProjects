package com.project.my.service.board;

import com.project.my.common.exception.ApiException;
import com.project.my.common.exception.ExceptionData;
import com.project.my.dto.board.BoardConfigDto;
import com.project.my.dto.board.BoardDto;
import com.project.my.dto.board.BoardInquiryDto;
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
    private final UsersRepository usersRepository;
    private final BoardRepositoryImpl boardRepoImpl;
    private final BoardConfigRepository boardConfigRepository;

    /*
    * 게시판 설정 변경
    * */

    public String boardConfigChange(BoardConfigDto configDto){
        BoardConfig config = boardConfigRepository.findByBoardType(configDto.boardType())
                .orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_BOARD_CONFIG));

        config.update(configDto);

        return "수정 되었습니다.";

    }



    /*
    *  글쓰기
    * */

    public Board boardInsert(Long userId, BoardDto dto) {
        Board board = null;
        BoardConfig config = configRepository.findByBoardType(dto.boardType())
                .orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_BOARD_CONFIG));

        Users users = usersRepository.getReferenceById(userId);

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

        return boardRepository.save(board);
    }

    /*
    * 글수정
    * */

    public long boardUpdate(BoardDto dto) {
        Board findBoard = boardRepository.findById(dto.boardId())
                        .orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_USER));

        findBoard.update(dto);

        return findBoard.getId();

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
//    public void commentInsert(CommentDto dto) {
//        Board board = boardRepository.findById(dto.boardId()).orElseThrow(() ->
//            new NotFoundElementException("해당 게시글을 찾을 수 없습니다."));
//        Comment comment = dto.toEntity("추후토큰값",board);
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
//    }
//
//    /*
//    * 댓글 삭제
//    * */
//
//    public void commentDelete(Long commentId) {
//        commentRepository.deleteById(commentId);
//    }
}
