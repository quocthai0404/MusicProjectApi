package com.music.project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_ERROR(500,"uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    DATA_NOT_FOUND(400, "data not found", HttpStatus.BAD_REQUEST),
    DATA_EXISTED(400, "data existed", HttpStatus.BAD_REQUEST);


    private int code;
    private HttpStatusCode statusCode;
    private String message;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }
}