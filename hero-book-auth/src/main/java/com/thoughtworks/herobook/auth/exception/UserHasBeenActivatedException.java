package com.thoughtworks.herobook.auth.exception;

public class UserHasBeenActivatedException extends RuntimeException{
    public UserHasBeenActivatedException() {
        super();
    }

    public UserHasBeenActivatedException(String message) {
        super(message);
    }
}
