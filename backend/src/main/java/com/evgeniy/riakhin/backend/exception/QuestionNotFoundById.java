package com.evgeniy.riakhin.backend.exception;

public class QuestionNotFoundById extends RuntimeException{
    public QuestionNotFoundById(String message) {
        super(message);
    }
}
