package com.quiz.quizprod.dao;

import com.quiz.quizprod.exception.AnswerUserFoundException;
import com.quiz.quizprod.exception.QuizExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;

import java.util.List;

public interface QuizDao extends BaseDao<Quiz> {


    boolean creatQuizWithQuestion(Long quizId, Question...question) throws AnswerUserFoundException, QuizExistsException;
    boolean deleteFromQuiz(Long quizId, Long...id) throws QuizExistsException;


}
