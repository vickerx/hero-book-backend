package com.thoughtworks.herobook.common.advice;

import com.thoughtworks.herobook.common.dto.ErrorResult;
import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ErrorResult> validateExceptionHandler(BindException bindException) {
        String errorMessage = bindException.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResult(ErrorCode.USER_INPUT_ERROR, errorMessage));
    }

    @ExceptionHandler(value = BasicException.class)
    public ResponseEntity<ErrorResult> errorHandler(BasicException basicException) {
        return ResponseEntity.status(basicException.getHttpStatus())
                .body(new ErrorResult(basicException.getErrorCode(), basicException.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResult> otherErrorsHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResult(ErrorCode.HERO_BOOK_INTERNAL_ERROR, exception.getMessage()));
    }
}
