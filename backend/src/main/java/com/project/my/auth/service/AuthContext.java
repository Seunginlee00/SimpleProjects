package com.project.my.auth.service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthContext {

    private Long userId;

    public Long userId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
