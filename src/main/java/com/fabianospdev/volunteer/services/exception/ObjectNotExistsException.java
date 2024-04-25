package com.fabianospdev.volunteer.services.exception;

public class ObjectNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ObjectNotExistsException( String message) {
        super( message );
    }
}