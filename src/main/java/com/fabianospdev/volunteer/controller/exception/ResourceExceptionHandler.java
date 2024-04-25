package com.fabianospdev.volunteer.controller.exception;

import com.fabianospdev.volunteer.services.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound( ObjectNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Not found", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<StandardError> invalidRequest(InvalidRequestException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Bad Request", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> unauthorized(UnauthorizedException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Unauthorized", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<StandardError> forbidden(ForbiddenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Forbidden", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<StandardError> internalServerError(InternalServerErrorException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Internal Server Error", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardError> userAlreadyExists(UserAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT; // 409 Conflict
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "User Already Exists", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}