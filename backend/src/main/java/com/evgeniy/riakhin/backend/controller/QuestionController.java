package com.evgeniy.riakhin.backend.controller;

import com.evgeniy.riakhin.backend.entity.Question;
import com.evgeniy.riakhin.backend.service.QuestionService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }
}
