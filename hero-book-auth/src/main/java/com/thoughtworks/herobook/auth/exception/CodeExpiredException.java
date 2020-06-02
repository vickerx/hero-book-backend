package com.thoughtworks.herobook.auth.exception;

public class CodeExpiredException extends RuntimeException {
    public CodeExpiredException() {
        super();
    }

    public CodeExpiredException(String message) {
        super(message);
    }
}
