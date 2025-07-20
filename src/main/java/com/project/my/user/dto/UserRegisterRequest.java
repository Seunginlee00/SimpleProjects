package com.project.my.user.dto;

import com.project.my.user.entity.Role;
import com.project.my.user.entity.Users;
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
