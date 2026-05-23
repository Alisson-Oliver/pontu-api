package br.com.pontu.api.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(
        ResourceNotFoundException e,
        HttpServletRequest request
    ) {

        StandardError err = new StandardError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Resource not found",
            e.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(err);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> businessError(
        BusinessException e,
        HttpServletRequest request
    ) {

        StandardError err = new StandardError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Business rule error",
            e.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(err);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<StandardError> duplicateResourceError(
        DuplicateResourceException e,
        HttpServletRequest request
    ) {
        StandardError err = new StandardError(
             LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Conflict resource error",
            e.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> genericError(
        Exception e,
        HttpServletRequest request
    ) {

        StandardError err = new StandardError(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            e.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(err);
    }
}