package com.thoughtworks.herobook.auth.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotUniqueException extends BasicException {
    public NotUniqueException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.NOT_UNIQUE_ERROR, message);
    }
}
