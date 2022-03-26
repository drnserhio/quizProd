package com.quiz.quizprod.model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "answersUsers")
public class AnswerUser extends DefaultEntity {

    private Long quizId;
    private String question;
    private String answerUser;
    private String answerSuccessful;
    private String usernameActive;
    private String idCode;
    private boolean isSuccessfulAnswer;
}
