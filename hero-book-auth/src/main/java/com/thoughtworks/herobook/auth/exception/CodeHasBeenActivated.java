package com.thoughtworks.herobook.auth.exception;

public class CodeHasBeenActivated extends RuntimeException{
    public CodeHasBeenActivated() {
        super();
    }

    public CodeHasBeenActivated(String message) {
        super(message);
    }
}
