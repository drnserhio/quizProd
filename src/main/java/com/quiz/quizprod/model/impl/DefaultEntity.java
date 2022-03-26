package com.quiz.quizprod.model.impl;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Getter
@Setter
public abstract class DefaultEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
