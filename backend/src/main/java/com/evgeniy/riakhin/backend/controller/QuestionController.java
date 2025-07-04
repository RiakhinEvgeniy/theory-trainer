package com.evgeniy.riakhin.backend.controller;

import com.evgeniy.riakhin.backend.dto.QuestionCreateDTO;
import com.evgeniy.riakhin.backend.dto.QuestionResponseDTO;
import com.evgeniy.riakhin.backend.service.QuestionService;
import com.evgeniy.riakhin.backend.util.NameMessage;
import com.evgeniy.riakhin.backend.util.NamePath;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(NamePath.API_QUESTIONS)
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getQuestions() {
        List<QuestionResponseDTO> questionResponseDTOS = questionService.findAll();
        return ResponseEntity.ok(questionResponseDTOS);
    }

    @GetMapping(NamePath.ID_PATH)
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable(name = "id") Long id) {
        QuestionResponseDTO questionResponseDTO = questionService.findById(id);
        return new ResponseEntity<>(questionResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping(NamePath.ID_PATH)
    public ResponseEntity<String> deleteQuestionById(@PathVariable(name = "id") Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(NameMessage.DELETED_SUCCESSFUL + id);
    }

    @PostMapping()
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody QuestionCreateDTO questionCreateDTO) {
        QuestionResponseDTO questionResponseDTO = questionService.saveQuestion(questionCreateDTO);
        return new ResponseEntity<>(questionResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping(NamePath.ID_PATH)
    public ResponseEntity<QuestionResponseDTO> updateQuestion(
            @PathVariable(name = "id") Long id,
            @RequestBody QuestionCreateDTO questionCreateDTO) {
        QuestionResponseDTO questionResponseDTO = questionService.questionUpdate(id, questionCreateDTO);
        return new ResponseEntity<>(questionResponseDTO, HttpStatus.OK);
    }
}
