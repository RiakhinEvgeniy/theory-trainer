package com.evgeniy.riakhin.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "correct_answer")
public class CorrectAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer")
    private String correctAnswer;

    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false, unique = true)
    private Question question;

    @Column(name = "variant")
    @Enumerated(EnumType.STRING)
    private VariantOfAnswer variantOfAnswer;

    public CorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
