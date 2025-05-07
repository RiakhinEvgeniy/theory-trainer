package com.evgeniy.riakhin.backend.exception;

public class UserNotFoundByName extends RuntimeException {
    public UserNotFoundByName(String message) {
        super(message);
    }
}
