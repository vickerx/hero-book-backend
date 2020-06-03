package com.thoughtworks.herobook.auth.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidEmailException extends BasicException {
    public InvalidEmailException() {
        super();
    }

    public InvalidEmailException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(httpStatus, errorCode, message);
    }
}
