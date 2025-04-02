package com.evgeniy.riakhin.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "correct_answer")
public class CorrectAnswer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "correctAnswer")
    private String correctAnswer;
}
