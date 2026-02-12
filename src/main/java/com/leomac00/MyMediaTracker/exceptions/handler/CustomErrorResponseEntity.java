package com.leomac00.MyMediaTracker.exceptions.handler;

import com.leomac00.MyMediaTracker.exceptions.ExceptionResponse;
import com.leomac00.MyMediaTracker.exceptions.InvalidJwtAuthException;
import com.leomac00.MyMediaTracker.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomErrorResponseEntity extends ResponseEntityExceptionHandler {

    private ResponseEntity<ExceptionResponse> build(HttpStatus status, Exception ex, WebRequest request) {
        ExceptionResponse body = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(body, status);
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(
            ResourceNotFoundException ex, WebRequest request) {
        return build(HttpStatus.NOT_FOUND, ex, request);
    }

    // 403 – permission denied
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDenied(
            Exception ex, WebRequest request) {
        return build(HttpStatus.FORBIDDEN, ex, request);
    }

    // 401 – not authenticated / bad credentials
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuth(
            Exception ex, WebRequest request) {
        return build(HttpStatus.UNAUTHORIZED, ex, request);
    }

    // 403 – invalid JWT
    @ExceptionHandler(InvalidJwtAuthException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidJwt(
            Exception ex, WebRequest request) {
        return build(HttpStatus.FORBIDDEN, ex, request);
    }

    // 500 – fallback (ALWAYS LAST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAll(
            Exception ex, WebRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }
}
