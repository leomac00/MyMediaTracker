package com.leomac00.MyMediaTracker.data.enums;

import lombok.Getter;

public enum ErrorMessage {
    USER_NOT_FOUND(404, "User requested was not found."),
    INVALID_CREDENTIALS(401, "Invalid credentials."),
    MEDIA_NOT_FOUND(404, "The Media requested was not found."),
    MEDIA_TYPE_NOT_FOUND(404, "The Media Type requested was not found.");

    private final int code;
    @Getter
    private final String message;

    ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
