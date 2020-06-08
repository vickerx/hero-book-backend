package com.thoughtworks.herobook.exception;

import com.thoughtworks.herobook.common.exception.BasicException;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class DownloadImageException extends BasicException {
    public DownloadImageException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.DOWNLOAD_IMAGE_ERROR, message);
    }
}
