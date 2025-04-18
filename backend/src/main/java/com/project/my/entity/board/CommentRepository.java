package com.project.my.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment,Long> {

    Comment findByBoard(Board board);

}
