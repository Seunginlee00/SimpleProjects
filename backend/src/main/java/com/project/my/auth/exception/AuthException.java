package com.project.my.auth.exception;

import com.smarttaekwondo.globepoint.common.exception.BaseException;
import com.smarttaekwondo.globepoint.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthException extends BaseException {

    private final AuthExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }

}
