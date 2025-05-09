package com.project.my.entity.board;

import com.project.my.entity.enums.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BoardConfigRepository extends JpaRepository<BoardConfig,Long>{
    Optional<BoardConfig> findByBoardType(BoardType boardType);

}
