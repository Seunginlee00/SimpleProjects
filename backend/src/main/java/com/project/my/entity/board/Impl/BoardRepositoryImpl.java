package com.project.my.entity.board.Impl;

import com.project.my.dto.board.BoardDto;
import com.project.my.dto.response.SearchDto;
import com.project.my.entity.board.Board;
import com.project.my.entity.board.QBoard;
import com.project.my.entity.board.QBoardConfig;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl {
    private final JPAQueryFactory query;
    private QBoard board = new QBoard("board");
    private QBoardConfig config = new QBoardConfig("config");

    public Page<Board> boardList(SearchDto dto, Pageable pageable){

        List<Board> boardList =
                query.select(board)
                .from(board)
                .where(deleteNo(),keywordDto(dto), boardType(dto))
                .orderBy(board.id.desc())
                .fetch();

        Long total = query.
                select(board.count())
                .from(board)
                .where(deleteNo(),keywordDto(dto), boardType(dto))
                .fetchOne();

        return new PageImpl<>(boardList, pageable, total);
    }

    // deleteNo
    private Predicate deleteNo(){
        return board.isDelete.eq(false);
    }

    // 게시판 종류
    private Predicate boardType(SearchDto dto){
        return dto.boardType() == null ? null : board.boardConfig.boardType.eq(dto.boardType());
    }

    // 키워드
    private Predicate keywordDto(SearchDto dto){
        BooleanExpression qK = null;

        if(dto.searchType() != null){
            if(dto.searchType().equals("content")){
                // 내용
                qK = board.content.contains(dto.searchValue());
            }else if (dto.searchType().equals("userName")){
                // 사용자
                qK = board.users.nickname.contains(dto.searchValue());
            }else {
                // 기본값 (제목)
                qK = board.subject.contains(dto.searchValue());
            }
        }
        return qK == null ? null : qK;
    }


}
