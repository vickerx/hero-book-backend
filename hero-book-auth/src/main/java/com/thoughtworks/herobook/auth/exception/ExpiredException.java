package com.thoughtworks.herobook.auth.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ExpiredException extends BasicException {
    public ExpiredException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CODE_HAS_EXPIRED, message);
    }
}
