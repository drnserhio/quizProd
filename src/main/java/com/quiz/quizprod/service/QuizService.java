package com.quiz.quizprod.service;

import com.quiz.quizprod.exception.domain.AnswerUserNotFoundException;
import com.quiz.quizprod.exception.domain.QuizExistsException;
import com.quiz.quizprod.model.impl.Quiz;

public interface QuizService extends BaseService<Quiz> {

    boolean creatQuizWithQuestion(Long quizId, Long... questioId) throws QuizExistsException, AnswerUserNotFoundException;

    boolean deleteFromQuiz(Long quizId, Long questionId) throws QuizExistsException;
}
