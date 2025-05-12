package com.evgeniy.riakhin.backend.dto;

public record QuestionResponseDTO(
        Long id,
        String questionText,
        CorrectAnswerResponseDTO correctResponseDTO) {
}
