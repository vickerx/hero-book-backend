package com.thoughtworks.herobook.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BasicException extends RuntimeException {
    private HttpStatus httpStatus;
    private ErrorCode errorCode;

    public BasicException() {
        super();
    }

    public BasicException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
