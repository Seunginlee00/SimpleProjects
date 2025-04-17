package com.project.my.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByIdAndIsDelete(Long id, Boolean isDelete);

}
