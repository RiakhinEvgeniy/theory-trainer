package com.evgeniy.riakhin.backend.entity;

import jakarta.persistence.*;
import lombok.*;

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
