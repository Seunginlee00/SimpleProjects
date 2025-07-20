package com.project.my.user.dto;

import com.project.my.user.entity.Role;
import com.project.my.user.entity.Users;
import java.time.LocalDateTime;
import java.util.Optional;

public record UserResponse(
    long id, // 고유 번호
    String loginId,
    LocalDateTime createDate,
    String email,
    String nickname,
    Role role
) {

  public UserResponse(Users user){
    this(
        user.getId(),
        Optional.of(user.getLoginId())
            .orElse("none"),
        Optional.of(user.getCreatedDate())
                .orElse(LocalDateTime.MIN),
        Optional.of(user.getEmail())
            .orElse("none"),
        Optional.of(user.getNickname())
            .orElse("none"),
        user.getRole());
  }

}
