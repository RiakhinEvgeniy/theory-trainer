package com.evgeniy.riakhin.backend.dto;

import com.evgeniy.riakhin.backend.entity.VariantOfAnswer;

import java.util.List;

public record QuestionCreateDTO(
        String textQuestion,
        String textCorrectAnswer,
        VariantOfAnswer variantOfAnswer,
        List<WrongAnswerCreateDTO> wrongAnswers) {

    // todo сделать валидацию
}
