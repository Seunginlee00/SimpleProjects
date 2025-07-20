package com.project.my.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    A("관리자"),
    M("일반 회원");

    private String label;
}
