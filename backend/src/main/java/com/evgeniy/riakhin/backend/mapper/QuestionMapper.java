package com.evgeniy.riakhin.backend.mapper;

import com.evgeniy.riakhin.backend.dto.*;
import com.evgeniy.riakhin.backend.entity.CorrectAnswer;
import com.evgeniy.riakhin.backend.entity.Question;
import com.evgeniy.riakhin.backend.entity.WrongAnswer;

import java.util.List;

public class QuestionMapper {

    public static QuestionResponseDTO toDTO(Question q) {
        CorrectAnswer correctAnswer = q.getCorrectAnswer();
        List<WrongAnswerResponseDTO> wrongAnswers = q.getWrongAnswers()
                .stream()
                .map(wrongAnswer -> new WrongAnswerResponseDTO(
                        wrongAnswer.getId(),
                        wrongAnswer.getWrongAnswer(),
                        wrongAnswer.getVariantOfAnswer().toString())).toList();
        CorrectAnswerResponseDTO correctAnswerResponseDTO = null;
        if (correctAnswer != null) {
            correctAnswerResponseDTO = new CorrectAnswerResponseDTO(
                    correctAnswer.getId(),
                    correctAnswer.getCorrectAnswer(),
                    correctAnswer.getVariantOfAnswer().toString()
            );
        }
        return new QuestionResponseDTO(q.getId(),
                q.getQuestion(),
                correctAnswerResponseDTO,
                wrongAnswers);
    }

    public static Question toEntity(QuestionCreateDTO dto) {
        Question q = new Question(dto.textQuestion());
        CorrectAnswer correctAnswer = new CorrectAnswer(dto.textCorrectAnswer());
        correctAnswer.setVariantOfAnswer(dto.variantOfAnswer());
        q.addCorrectAnswer(correctAnswer);
        for (WrongAnswerCreateDTO wrongAnswerCreateDTO : dto.wrongAnswers()) {
            WrongAnswer wr = new WrongAnswer();
            wr.setWrongAnswer(wrongAnswerCreateDTO.answer());
            wr.setVariantOfAnswer(wrongAnswerCreateDTO.variantOfAnswer());

            q.addWrongAnswer(wr);
        }
        return q;
    }
}
