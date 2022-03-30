package com.quiz.quizprod.dao;

import com.quiz.quizprod.exception.PasswordInvalidException;
import com.quiz.quizprod.exception.QuizExistsException;
import com.quiz.quizprod.exception.UserExistsException;
import com.quiz.quizprod.exception.UsernameExistsException;
import com.quiz.quizprod.model.impl.AnswerUser;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.User;

import java.util.List;

public interface UserDao extends BaseDao<User>{

    User registerAccount(User user) throws UserExistsException, PasswordInvalidException, UsernameExistsException;
    User getUserByUsername(String username);

    List<Quiz> getFreeQuizByUserId(Long userId);

    boolean answerTheQuestion(String quizId, String username, String answer, Question question) throws QuizExistsException;

    boolean insertQuizToUserAfterTest(Long quizId, Long userId);

}


