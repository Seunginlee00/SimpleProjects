package com.project.my.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardType {
    N("공지사항"),
    F("자유 게시판 ");
    private String label;
}
