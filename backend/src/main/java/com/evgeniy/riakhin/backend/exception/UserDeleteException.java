package com.evgeniy.riakhin.backend.exception;

public class UserDeleteException extends RuntimeException{
    public UserDeleteException(String message) {
        super(message);
    }
}
