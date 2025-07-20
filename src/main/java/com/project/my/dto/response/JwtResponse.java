package com.project.my.dto.response;

public record JwtResponse(
    String result,
    String message,
    boolean isExpired,
    String accessToken,
    String refreshToken
) {
}
