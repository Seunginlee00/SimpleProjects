package com.project.my.common.dto.response;

public record JwtResponse(
    String result,
    String message,
    boolean isExpired,
    String accessToken,
    String refreshToken
) {
}
