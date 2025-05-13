package com.evgeniy.riakhin.backend.controller;

import com.evgeniy.riakhin.backend.dto.QuestionResponseDTO;
import com.evgeniy.riakhin.backend.service.QuestionService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getQuestions() {
        List<QuestionResponseDTO> questionResponseDTOS = questionService.findAll();
        return ResponseEntity.ok(questionResponseDTOS);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable(name = "id") Long id) {
        QuestionResponseDTO questionResponseDTO = questionService.findById(id);
        return new ResponseEntity<>(questionResponseDTO, HttpStatus.OK);
    }
}
