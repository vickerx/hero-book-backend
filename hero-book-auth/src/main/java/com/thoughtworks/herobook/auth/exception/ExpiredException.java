package com.thoughtworks.herobook.auth.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ExpiredException extends BasicException {
    public ExpiredException() {
        super();
    }

    public ExpiredException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(httpStatus, errorCode, message);
    }
}
