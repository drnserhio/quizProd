package com.quiz.quizprod.dao;


import com.quiz.quizprod.model.impl.AnswerUser;

import java.util.List;

public interface AnswerUserDao extends BaseDao<AnswerUser>  {

    List<AnswerUser> getAllPassedTestByQuizId(String quizId, String username);
}
