package com.evgeniy.riakhin.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;

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

    @ExceptionHandler(QuestionNotFoundById.class)
    public ResponseEntity<ErrorResponse> handleQuestionNotFoundById(QuestionNotFoundById ex, HttpServletRequest req) {
        ErrorResponse errorResponse = createErrorResponse(ex, req, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleObjectOptimisticLockingFailureException(
            ObjectOptimisticLockingFailureException exception, HttpServletRequest req) {
        ErrorResponse errorResponse = createErrorResponse(exception, req, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConcurrentModificationException.class)
    public ResponseEntity<ErrorResponse> handleConcurrentModificationException(
            ConcurrentModificationException exception, HttpServletRequest req) {
        ErrorResponse errorResponse = createErrorResponse(exception, req, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest req) {
        ErrorResponse errorResponse = createErrorResponse(exception, req, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(Exception exception, HttpServletRequest req, HttpStatus status) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                exception.getMessage(),
                req.getRequestURI()
        );
    }
}
