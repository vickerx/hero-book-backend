package com.thoughtworks.herobook.common.exception;

import org.springframework.http.HttpStatus;

public class ParamsValidateException extends BasicException {
    public ParamsValidateException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.USER_INPUT_ERROR, message);
    }
}
