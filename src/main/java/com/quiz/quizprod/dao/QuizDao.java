package com.quiz.quizprod.dao;

import com.quiz.quizprod.exception.domain.AnswerUserNotFoundException;
import com.quiz.quizprod.exception.domain.QuestionNotFoundException;
import com.quiz.quizprod.exception.domain.QuizExistsException;
import com.quiz.quizprod.model.impl.Quiz;

public interface QuizDao extends BaseDao<Quiz> {


    boolean creatQuizWithQuestion(Long quizId, Long...questionId) throws AnswerUserNotFoundException, QuizExistsException;
    boolean deleteFromQuiz(Long quizId, Long questionId) throws QuizExistsException;

}
