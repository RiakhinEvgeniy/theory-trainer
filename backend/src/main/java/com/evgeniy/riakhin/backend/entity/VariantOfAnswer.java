package com.evgeniy.riakhin.backend.entity;

import lombok.Getter;

@Getter
public enum VariantOfAnswer {
    CORRECT(true, "Correct answer"),
    INCORRECT(false, "Incorrect answer");

    private final boolean correct;
    private final String description;

    VariantOfAnswer(boolean correct, String description) {
        this.correct = correct;
        this.description = description;
    }
}
