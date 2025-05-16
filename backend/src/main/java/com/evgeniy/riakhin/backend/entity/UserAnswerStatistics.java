package com.evgeniy.riakhin.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "user_answer_statistics")
public class UserAnswerStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    private Question question;

    @Column(name = "chosen_answer_id")
    private Long chosenAnswerId;

    @Column(name = "variant")
    @Enumerated(EnumType.STRING)
    private VariantOfAnswer variantOfAnswer;

    @CreationTimestamp
    @Column(name = "answered_at", updatable = false)
    private LocalDateTime answeredAt;
}
