package com.fabianospdev.volunteer.services.exception;

public class UserNotExistsException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException(String message) {
        super( message );
    }
}