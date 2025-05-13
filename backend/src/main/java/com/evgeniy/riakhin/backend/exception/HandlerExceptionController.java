package com.evgeniy.riakhin.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerExceptionController {
    @ExceptionHandler(UserNotFoundById.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundById(UserNotFoundById ex, HttpServletRequest req) {
        ErrorResponse errorResponse = createErrorResponse(ex, req, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundByName.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundByName(UserNotFoundByName ex, HttpServletRequest req) {
        ErrorResponse errorResponse = createErrorResponse(ex, req, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest req) {
        ErrorResponse errorResponse = createErrorResponse(ex, req, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QuestionNotFoundById.class)
    public ResponseEntity<ErrorResponse> handleQuestionNotFoundById(QuestionNotFoundById ex, HttpServletRequest req) {
        ErrorResponse errorResponse = createErrorResponse(ex, req, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private ErrorResponse createErrorResponse(Exception ex, HttpServletRequest req, HttpStatus status) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                ex.getMessage(),
                req.getRequestURI()
        );
    }
}
