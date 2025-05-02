package com.evgeniy.riakhin.backend.dto;

import com.evgeniy.riakhin.backend.entity.Question;

public record QuestionResponseDTO(
        Long id,
        String questionText,
        CorrectAnswerResponseDTO correctResponseDTO) {
    public static QuestionResponseDTO fromEntity(Question question) {
        return new QuestionResponseDTO(
                question.getId(),
                question.getQuestion(),
                question.getCorrectAnswer() != null ? CorrectAnswerResponseDTO.fromEntity(question.getCorrectAnswer()) : null);
    }
}
