package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.QuestionResponseDTO;
import com.evgeniy.riakhin.backend.entity.Question;
import com.evgeniy.riakhin.backend.exception.QuestionNotFoundById;
import com.evgeniy.riakhin.backend.mapper.QuestionMapper;
import com.evgeniy.riakhin.backend.repository.QuestionRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .orElseThrow(() -> new QuestionNotFoundById("Question not found by id: " + id));
        return QuestionMapper.toDTO(question);
    }
}
