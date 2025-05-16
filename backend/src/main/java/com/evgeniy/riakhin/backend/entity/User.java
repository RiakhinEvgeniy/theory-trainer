package com.evgeniy.riakhin.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<UserAnswerStatistics> userAnswerStatistics = new ArrayList<>();

    @Version
    Long version;

    public void addUserAnswerStatistic(UserAnswerStatistics userAnswerStat) {
        this.userAnswerStatistics.add(userAnswerStat);
        userAnswerStat.setUser(this);
    }

    public void removeUserAnswerStatistic(UserAnswerStatistics userAnswerStat) {
        if (userAnswerStat != null) {
            this.userAnswerStatistics.remove(userAnswerStat);
            userAnswerStat.setUser(null);
        }
    }
}
