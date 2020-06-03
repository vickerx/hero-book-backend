package com.thoughtworks.herobook.auth.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BasicException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(httpStatus, errorCode, message);
    }
}
