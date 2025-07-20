package com.project.my.board.repository;

import com.project.my.board.entity.Board;
import com.project.my.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment,Long> {

    Comment findByBoard(Board board);

}
