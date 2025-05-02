package com.evgeniy.riakhin.backend.dto;

import com.evgeniy.riakhin.backend.entity.CorrectAnswer;

public record CorrectAnswerResponseDTO(
        Long id,
        String correctAnswer,
        String variant) {
    public static CorrectAnswerResponseDTO fromEntity(CorrectAnswer correctAnswer) {
        return new CorrectAnswerResponseDTO(
                correctAnswer.getId(),
                correctAnswer.getCorrectAnswer(),
                correctAnswer.getVariantOfAnswer().toString());
    }
}
