package com.evgeniy.riakhin.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "question", nullable = false)
    private String question;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private CorrectAnswer correctAnswer;

    public Question(String question) {
        this.question = question;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<WrongAnswer> wrongAnswers = new ArrayList<>();

    public void addWrongAnswer(WrongAnswer wr) {
        wrongAnswers.add(wr);
        wr.setQuestion(this);
    }

    public void removeWrongAnswer(WrongAnswer wr) {
        wrongAnswers.remove(wr);
        wr.setQuestion(null);
    }

    public void addCorrectAnswer(CorrectAnswer correctAnswer) {
        this.correctAnswer = correctAnswer;
        correctAnswer.setQuestion(this);
    }

    public void removeCorrectAnswer() {
        if(correctAnswer != null) {
            correctAnswer.setQuestion(null);
            correctAnswer = null;
        }
    }
}
