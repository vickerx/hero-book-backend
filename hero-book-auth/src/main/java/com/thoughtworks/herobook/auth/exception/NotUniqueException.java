package com.thoughtworks.herobook.auth.exception;

public class NotUniqueException extends RuntimeException{
    public NotUniqueException(String message) {
        super(message);
    }
}
