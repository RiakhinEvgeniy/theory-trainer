package com.evgeniy.riakhin.backend.dto;

public record CorrectAnswerResponseDTO(
        Long id,
        String answer,
        String variant) {
}
