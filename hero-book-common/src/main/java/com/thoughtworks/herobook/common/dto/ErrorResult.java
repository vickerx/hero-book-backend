package com.thoughtworks.herobook.common.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.herobook.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResult {

    @JsonProperty("error_code")
    private String errorCode;

    private String message;

    public ErrorResult(ErrorCode errorCode) {
        this.errorCode = errorCode.getValue();
    }

    public ErrorResult(ErrorCode errorCode, String message) {
        this.errorCode = errorCode.getValue();
        this.message = message;
    }
}
