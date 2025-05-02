package com.evgeniy.riakhin.backend.repository;

import com.evgeniy.riakhin.backend.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query("select q from Question q")
    List<Question> getAllQuestions();
}
