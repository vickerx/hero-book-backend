package com.thoughtworks.herobook.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UploadImageException extends BasicException {
    public UploadImageException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.UPLOAD_IMAGE_ERROR, message);
    }
}
