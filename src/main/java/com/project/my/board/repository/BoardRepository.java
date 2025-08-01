package com.project.my.board.repository;

import com.project.my.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByIdAndIsDelete(Long id, Boolean isDelete);

}
