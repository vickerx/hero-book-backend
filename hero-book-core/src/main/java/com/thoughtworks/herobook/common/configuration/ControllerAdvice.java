package com.thoughtworks.herobook.common.configuration;

import com.thoughtworks.herobook.common.exception.ErrorCode;
import com.thoughtworks.herobook.common.exception.ResourceNotFoundException;
import com.thoughtworks.herobook.dto.error.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResult> handleOtherException(Throwable e) {
        log.error("system error: ", e);
        ErrorResult errorResult = new ErrorResult(ErrorCode.HERO_BOOK_INTERNAL_ERROR, "system error");
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResult(ErrorCode.RESOURCE_NOT_FOUND, e.getMessage());
    }
}
