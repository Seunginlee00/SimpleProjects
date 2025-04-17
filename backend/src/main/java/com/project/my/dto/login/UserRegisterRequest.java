package com.project.my.dto.login;

import com.java.project.api.entity.enums.Role;
import com.java.project.api.entity.user.Users;

import java.util.Optional;

public record UserRegisterRequest(
    String loginId,
    String email,
    String password,
    String nickname,
    Role role,
    Long userId
) {

    public Users toEntity(String encodePassword ){
        return Users.builder()
                .loginId(loginId())
                .email(email())
                .password(encodePassword)
                .nickname(nickname())
                .role(Optional.of(role())
                        .orElse(Role.M))
                .build();
    }

}
