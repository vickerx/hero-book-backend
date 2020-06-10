package com.thoughtworks.herobook.auth.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserNotActivatedException extends BasicException {
    public UserNotActivatedException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.ACCOUNT_HAS_NOT_BEEN_ACTIVATED, message);
    }
}
