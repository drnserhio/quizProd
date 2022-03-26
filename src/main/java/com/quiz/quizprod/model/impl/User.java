package com.quiz.quizprod.model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
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

    @OneToMany(cascade = ALL, fetch = LAZY)
    private List<Quiz> quizzes;

}
