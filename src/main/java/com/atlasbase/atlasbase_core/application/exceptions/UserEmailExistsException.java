package com.atlasbase.atlasbase_core.application.exceptions;

public class UserEmailExistsException extends RuntimeException {
    public UserEmailExistsException(String message) {
        super(message);
    }
}
