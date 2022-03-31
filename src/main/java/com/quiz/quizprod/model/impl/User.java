package com.quiz.quizprod.model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User extends DefaultEntity {

    private String username;
    private String password;
    private String role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinTable(
            name = "users_quizzes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "quizzes_id")
    )
    private List<Quiz> quizzes;

}
