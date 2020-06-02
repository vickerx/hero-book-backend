package com.thoughtworks.herobook.auth.exception;

public class ExpiredException extends RuntimeException {
    public ExpiredException() {
        super();
    }

    public ExpiredException(String message) {
        super(message);
    }
}
