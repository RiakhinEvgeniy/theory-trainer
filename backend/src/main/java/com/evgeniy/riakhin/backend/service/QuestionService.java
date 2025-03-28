package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.model.Question;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class QuestionService {
    public List<Question> getQuestions() {
        return List.of(
                new Question("question1"),
                new Question("question2"),
                new Question("question3"),
                new Question("question4"),
                new Question("question5"));
    }
}
