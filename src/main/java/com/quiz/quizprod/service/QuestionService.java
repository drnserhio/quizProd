package com.quiz.quizprod.service;

import com.quiz.quizprod.model.impl.Question;

import java.util.List;

public interface QuestionService extends BaseService<Question> {

    List<Question> getAllQuestionWithoutInQuiz(String quizId);
}
