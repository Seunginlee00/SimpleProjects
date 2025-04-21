package com.project.my.config.jwt;

public record JWTException(
        boolean isExpired,
        String message
) {

}
