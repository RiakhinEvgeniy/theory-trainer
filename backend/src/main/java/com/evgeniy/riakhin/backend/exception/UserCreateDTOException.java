package com.evgeniy.riakhin.backend.exception;

public class UserCreateDTOException extends RuntimeException {
    public UserCreateDTOException(String message) {
        super(message);
    }
}
