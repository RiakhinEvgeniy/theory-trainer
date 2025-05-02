package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.QuestionResponseDTO;
import com.evgeniy.riakhin.backend.repository.QuestionRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public List<QuestionResponseDTO> findAll() {
        return questionRepository.getAllQuestions().stream().map(QuestionResponseDTO::fromEntity).toList();
    }
}
