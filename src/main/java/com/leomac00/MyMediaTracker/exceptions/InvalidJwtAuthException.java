package com.leomac00.MyMediaTracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthException extends AuthenticationException {
    private static final Long serialVersionUID = 1L;
    public InvalidJwtAuthException(String msg) {
        super(msg);
    }
}
