package com.evgeniy.riakhin.backend.dto;

public record WrongAnswerResponseDTO(
        Long id,
        String answer,
        String variant) {
}
