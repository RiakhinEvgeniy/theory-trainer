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


    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<WrongAnswer> wrongAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<UserAnswerStatistics> userAnswerStatistics = new ArrayList<>();

    public Question(String question) {
        this.question = question;
    }

    public void addWrongAnswer(WrongAnswer wr) {
        wrongAnswers.add(wr);
        wr.setQuestion(this);
    }

    public void removeWrongAnswer(WrongAnswer wrongAnswer) {
        wrongAnswers.remove(wrongAnswer);
        wrongAnswer.setQuestion(null);
    }

    public void addCorrectAnswer(CorrectAnswer correctAnswer) {
        this.correctAnswer = correctAnswer;
        correctAnswer.setQuestion(this);
    }

    public void removeCorrectAnswer() {
        if (this.correctAnswer != null) {
            this.correctAnswer.setQuestion(null);
            this.correctAnswer = null;
        }
    }

    public void addUserAnswerStatistics(UserAnswerStatistics userAnswerStat) {
        this.userAnswerStatistics.add(userAnswerStat);
        userAnswerStat.setQuestion(this);
    }

    public void removeUserAnswerStatistics(UserAnswerStatistics userAnswerStat) {
        if (userAnswerStat != null) {
            userAnswerStatistics.remove(userAnswerStat);
            userAnswerStat.setQuestion(null);
        }
    }
}
