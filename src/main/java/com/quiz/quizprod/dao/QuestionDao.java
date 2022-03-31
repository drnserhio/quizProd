package com.quiz.quizprod.dao;

import com.quiz.quizprod.model.impl.Question;

import java.util.List;

public interface QuestionDao extends BaseDao<Question> {

    List<Question> getAllQuestionWithoutInQuiz(String quizId);

}
