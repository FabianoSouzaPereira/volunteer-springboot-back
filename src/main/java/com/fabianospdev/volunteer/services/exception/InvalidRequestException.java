package com.fabianospdev.volunteer.services.exception;

public class InvalidRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidRequestException(String msg){
        super(msg);
    }
}