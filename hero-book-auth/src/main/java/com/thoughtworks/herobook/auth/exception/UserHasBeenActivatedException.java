package com.thoughtworks.herobook.auth.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserHasBeenActivatedException extends BasicException {
    public UserHasBeenActivatedException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.ACCOUNT_HAS_BEEN_ACTIVATED, message);
    }
}
