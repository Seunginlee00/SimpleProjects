package com.project.my.board.repository;

import com.project.my.board.entity.BoardConfig;
import com.project.my.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardConfigRepository extends JpaRepository<BoardConfig,Long>{
    BoardConfig findByBoardType(BoardType boardType);

}
