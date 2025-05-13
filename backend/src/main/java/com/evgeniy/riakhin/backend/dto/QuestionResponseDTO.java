package com.evgeniy.riakhin.backend.dto;

import java.util.List;

public record QuestionResponseDTO(
        Long id,
        String questionText,
        CorrectAnswerResponseDTO correctResponseDTO,
        List<WrongAnswerResponseDTO> wrongAnswerResponseDTOS) {
}
