package com.evgeniy.riakhin.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue()
    private Long id;

    @Column(name = "question", nullable = false)
    private String question;

    public Question(String question) {
        this.question = question;
    }
}
