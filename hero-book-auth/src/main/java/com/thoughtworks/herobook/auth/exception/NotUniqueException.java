package com.thoughtworks.herobook.auth.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotUniqueException extends BasicException {
    public NotUniqueException() {
        super();
    }

    public NotUniqueException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(httpStatus, errorCode, message);
    }
}
