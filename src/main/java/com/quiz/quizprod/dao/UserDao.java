package com.quiz.quizprod.dao;

import com.quiz.quizprod.exception.domain.PasswordInvalidException;
import com.quiz.quizprod.exception.domain.QuizExistsException;
import com.quiz.quizprod.exception.domain.UserExistsException;
import com.quiz.quizprod.exception.domain.UsernameExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.User;

import java.util.List;

public interface UserDao extends BaseDao<User>{

    User registerAccount(User user) throws UserExistsException, PasswordInvalidException, UsernameExistsException;
    User registerAccountAdmin(User user) throws UserExistsException, PasswordInvalidException, UsernameExistsException;
    User getUserByUsername(String username);

    List<Quiz> getFreeQuizByUserId(Long userId);

    boolean isExistByUsername(String username);

    boolean answerTheQuestion(String quizId, String username, String answer, Question question) throws QuizExistsException;

    boolean insertQuizToUserAfterTest(Long quizId, Long userId);

}


