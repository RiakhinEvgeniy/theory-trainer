package com.evgeniy.riakhin.backend.exception;

public class UserNotFoundById extends RuntimeException{
    public UserNotFoundById(String message) {
        super(message);
    }
}
