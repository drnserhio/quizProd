package com.quiz.quizprod.model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "quizes")
public class Quiz extends DefaultEntity {

    private String name;
    private Date dateStart;
    private Date dateEnd;
    private String notice;
    private long countQuestion;

    @OneToMany(cascade = ALL, fetch = LAZY)
    private List<Question> questions;
}
