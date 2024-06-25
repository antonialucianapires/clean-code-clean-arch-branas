package com.antonialucianapires.taxi_online.infrastructure.config.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.antonialucianapires.taxi_online.domain.exception.AccountAlreadyExistsException;
import com.antonialucianapires.taxi_online.domain.exception.AccountNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String EMPTY_PATH = "";

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAccountAlreadyExistsException(AccountAlreadyExistsException exception) {
        log.error("AccountAlreadyExistsException: {}", exception.getMessage());
        ErrorResponse response = createErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), "/api/accounts/signup");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException exception) {
        log.error("AccountNotFoundException: {}", exception.getMessage());
        ErrorResponse response = createErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), "/api/accounts/" + exception.getAccountId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("IllegalArgumentException: {}", exception.getMessage());
        ErrorResponse response = createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), EMPTY_PATH);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Exception: {}", exception.getMessage());
        ErrorResponse response = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", EMPTY_PATH);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(HttpStatus status, String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    }
}
