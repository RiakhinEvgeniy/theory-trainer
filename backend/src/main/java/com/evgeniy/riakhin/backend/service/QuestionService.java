package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.CorrectAnswerCreateDTO;
import com.evgeniy.riakhin.backend.dto.QuestionCreateDTO;
import com.evgeniy.riakhin.backend.dto.QuestionResponseDTO;
import com.evgeniy.riakhin.backend.dto.WrongAnswerCreateDTO;
import com.evgeniy.riakhin.backend.entity.CorrectAnswer;
import com.evgeniy.riakhin.backend.entity.Question;
import com.evgeniy.riakhin.backend.entity.WrongAnswer;
import com.evgeniy.riakhin.backend.exception.QuestionNotFoundById;
import com.evgeniy.riakhin.backend.mapper.QuestionMapper;
import com.evgeniy.riakhin.backend.repository.QuestionRepository;
import com.evgeniy.riakhin.backend.util.NameException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> findAll() {
        List<Question> allQuestions = questionRepository.getAllQuestions();
        return allQuestions.stream().map(QuestionMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public QuestionResponseDTO findById(Long id) {
        Question question = questionRepository.findQuestionById(id)
                .orElseThrow(() -> new QuestionNotFoundById(NameException.QUESTION_NOT_FOUND_BY_ID + id));
        return QuestionMapper.toDTO(question);
    }

    @Transactional
    public QuestionResponseDTO saveQuestion(QuestionCreateDTO questionCreateDTO) {
        Question question = QuestionMapper.toEntity(questionCreateDTO);
        question = questionRepository.save(question);
        return QuestionMapper.toDTO(question);
    }

    @Transactional
    public QuestionResponseDTO questionUpdate(Long id, QuestionCreateDTO questionCreateDTO) {
        Question question = questionRepository.findQuestionById(id)
                .orElseThrow(() -> new QuestionNotFoundById(NameException.QUESTION_NOT_FOUND_BY_ID + id));
        updateQuestionFields(questionCreateDTO, question);

        question = questionRepository.save(question);
        return QuestionMapper.toDTO(question);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new QuestionNotFoundById(NameException.QUESTION_NOT_FOUND_BY_ID + id);
        }
        questionRepository.deleteById(id);
    }

    private void updateQuestionFields(QuestionCreateDTO questionCreateDTO, Question question) {

        if (!Objects.equals(question.getQuestion(), questionCreateDTO.textQuestion())) {
            question.setQuestion(questionCreateDTO.textQuestion());
        }

        CorrectAnswerCreateDTO correctAnswerCreateDTO = questionCreateDTO.correctAnswerCreateDTO();

        if (correctAnswerCreateDTO != null) {

            CorrectAnswer existingCorrectAnswer = question.getCorrectAnswer();

            if (existingCorrectAnswer == null) {
                CorrectAnswer newCorrectAnswer = new CorrectAnswer(correctAnswerCreateDTO.correctAnswer());
                newCorrectAnswer.setVariantOfAnswer(correctAnswerCreateDTO.variantOfAnswer());
                question.addCorrectAnswer(newCorrectAnswer);
            } else {
                existingCorrectAnswer.setCorrectAnswer(correctAnswerCreateDTO.correctAnswer());
                existingCorrectAnswer.setVariantOfAnswer(correctAnswerCreateDTO.variantOfAnswer());
            }
        }

        for (WrongAnswer oldWrongAnswer : new ArrayList<>(question.getWrongAnswers())) {
            question.removeWrongAnswer(oldWrongAnswer);
        }

        for (WrongAnswerCreateDTO newWrongAnswer : questionCreateDTO.wrongAnswers()) {
            WrongAnswer wrongAnswer = new WrongAnswer(newWrongAnswer.answer());
            wrongAnswer.setVariantOfAnswer(newWrongAnswer.variantOfAnswer());
            question.addWrongAnswer(wrongAnswer);
        }
    }
}
