package com.project.my.dto.login;

import com.project.my.entity.enums.Role;
import com.project.my.entity.user.Users;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String loginId,
        String email,
        String password,
        String nickname,
        Role role,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
    public UserResponseDto(Users users){
        this(users.getId(),
                users.getLoginId(),
                users.getEmail(),
                users.getPassword(),
                users.getNickname(),
                users.getRole(),
                users.getCreatedDate(),
                users.getModifiedDate());
    }

    public static UserResponseDto from(Users users){
        return new UserResponseDto(
                users.getId(),
                users.getLoginId(),
                users.getEmail(),
                users.getPassword(),
                users.getNickname(),
                users.getRole(),
                users.getCreatedDate(),
                users.getModifiedDate()
        );
    }
}
