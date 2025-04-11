package com.evgeniy.riakhin.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "correct_answer")
public class CorrectAnswer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "correct_answer")
    private String correctAnswer;
}
