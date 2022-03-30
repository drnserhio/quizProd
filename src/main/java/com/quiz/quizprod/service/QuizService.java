package com.quiz.quizprod.service;

import com.quiz.quizprod.exception.AnswerUserFoundException;
import com.quiz.quizprod.exception.QuizExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;

public interface QuizService extends BaseService<Quiz> {

    boolean creatQuizWithQuestion(Long quizId, Long... questioId) throws QuizExistsException, AnswerUserFoundException;
}
