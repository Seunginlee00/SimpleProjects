package com.project.my.common.dto.login;


public record TokenInfo(
        String grantType,
        String accessToken
) {

    public TokenInfo(String grantType, String accessToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }
}
