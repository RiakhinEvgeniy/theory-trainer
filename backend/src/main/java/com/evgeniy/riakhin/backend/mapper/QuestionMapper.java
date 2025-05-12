package com.evgeniy.riakhin.backend.mapper;

import com.evgeniy.riakhin.backend.dto.CorrectAnswerResponseDTO;
import com.evgeniy.riakhin.backend.dto.QuestionResponseDTO;
import com.evgeniy.riakhin.backend.entity.CorrectAnswer;
import com.evgeniy.riakhin.backend.entity.Question;

public class QuestionMapper {

    public static QuestionResponseDTO toDTO(Question q) {
        CorrectAnswer correctAnswer = q.getCorrectAnswer();
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
                correctAnswerResponseDTO);
    }
}
