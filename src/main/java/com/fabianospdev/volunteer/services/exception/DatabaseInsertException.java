package com.fabianospdev.volunteer.services.exception;

public class DatabaseInsertException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DatabaseInsertException(String msg) {super(msg);}
}