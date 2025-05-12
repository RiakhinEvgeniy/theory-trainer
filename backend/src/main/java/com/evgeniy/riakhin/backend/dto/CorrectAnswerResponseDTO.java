package com.evgeniy.riakhin.backend.dto;

public record CorrectAnswerResponseDTO(
        Long id,
        String correctAnswer,
        String variant) {
}
