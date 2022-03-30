package com.quiz.quizprod.service;

import com.quiz.quizprod.model.impl.AnswerUser;

import java.util.List;

public interface AnwerUserService extends BaseService<AnswerUser> {

    List<AnswerUser> getAllPassedTestByQuizId(String quizId, String username);
}
