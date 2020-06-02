package com.thoughtworks.herobook.common.exception;

import java.util.Arrays;

public enum ErrorCode {
    RESOURCE_NOT_FOUND("resource_not_found"),
    INVALIDATE_PARAMS("invalidate params"),
    HERO_BOOK_INTERNAL_ERROR("hero_book_internal_error"),
    ;

    private String value;

    private String descriptionCn;

    ErrorCode(String errCode) {
        this.value = errCode;
    }

    ErrorCode(String errCode, String descriptionCn) {
        this.value = errCode;
        this.descriptionCn = descriptionCn;
    }

    public String getValue() {
        return value;
    }

    public String getDescriptionCn() {
        return descriptionCn;
    }

    public static ErrorCode fromValue(String value) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.value.equals(value)).findFirst()
                .orElse(null);
    }
}
