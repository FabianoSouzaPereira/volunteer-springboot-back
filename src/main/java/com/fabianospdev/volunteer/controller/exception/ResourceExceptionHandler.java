package com.fabianospdev.volunteer.controller.exception;

import com.fabianospdev.volunteer.services.exception.DatabaseInsertException;
import com.fabianospdev.volunteer.services.exception.ForbiddenException;
import com.fabianospdev.volunteer.services.exception.InternalServerErrorException;
import com.fabianospdev.volunteer.services.exception.InvalidRequestException;
import com.fabianospdev.volunteer.services.exception.ObjectAlreadyExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import com.fabianospdev.volunteer.services.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        return error(HttpStatus.NOT_FOUND, "Not found", ex.getMessage(), request);
    }

    @ExceptionHandler(ObjectNotExistsException.class)
    public ResponseEntity<StandardError> objectNotExists(ObjectNotExistsException ex, HttpServletRequest request) {
        return error(HttpStatus.NOT_FOUND, "Not found", ex.getMessage(), request);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<StandardError> objectAlreadyExists(ObjectAlreadyExistsException ex, HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler({InvalidRequestException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<StandardError> badRequest(Exception ex, HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));
        return error(HttpStatus.BAD_REQUEST, "Validation failed", message, request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> unauthorized(UnauthorizedException ex, HttpServletRequest request) {
        return error(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<StandardError> forbidden(ForbiddenException ex, HttpServletRequest request) {
        return error(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), request);
    }

    @ExceptionHandler({DatabaseInsertException.class, InternalServerErrorException.class})
    public ResponseEntity<StandardError> serverError(RuntimeException ex, HttpServletRequest request) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), request);
    }

    private ResponseEntity<StandardError> error(HttpStatus status, String error, String message, HttpServletRequest request) {
        StandardError body = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error(error)
                .message(message)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(body);
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}
