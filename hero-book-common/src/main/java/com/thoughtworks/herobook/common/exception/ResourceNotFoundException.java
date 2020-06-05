package com.thoughtworks.herobook.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BasicException{
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND, message);
    }
}
