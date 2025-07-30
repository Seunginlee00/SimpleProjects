package com.project.my.user.dto;

import com.project.my.user.entity.Users;

public record UsersRequest(
    Long id,
    String loginId,
    String userNm,
    String email,
    String mobileNo,
    int access,
    int display
) {

  public Users toEntity( String encodePasswd) {
    return Users.builder()
        .id(id())
        .loginId(loginId())
        .userNm(userNm())
        .email(email())
        .mobileNo(mobileNo())
        .passwd(encodePasswd)
        .build();
  }


}