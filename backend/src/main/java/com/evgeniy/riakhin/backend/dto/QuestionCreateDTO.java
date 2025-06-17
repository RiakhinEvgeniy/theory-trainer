package com.evgeniy.riakhin.backend.dto;


import java.util.List;

public record QuestionCreateDTO(
        String textQuestion,
        CorrectAnswerCreateDTO correctAnswerCreateDTO,
        List<WrongAnswerCreateDTO> wrongAnswers) {

    // todo сделать валидацию
}
