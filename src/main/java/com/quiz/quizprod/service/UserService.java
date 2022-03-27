package com.quiz.quizprod.service;

import com.quiz.quizprod.exception.PasswordInvalidException;
import com.quiz.quizprod.exception.QuizExistsException;
import com.quiz.quizprod.exception.UserExistsException;
import com.quiz.quizprod.exception.UsernameExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends BaseService<User>, UserDetailsService {

    User registerAccount(User user) throws UserExistsException, PasswordInvalidException, UsernameExistsException;
    User getUserByUsername(String username);

    List<Quiz> getFreeQuizByUserId(Long userId);

    Quiz onSelectQuiz(Long quizId);

    boolean answerTheQuestion(Long quizId, String username, String answer, Question question) throws QuizExistsException;

    void insertQuizToUserAfterTest(Long quizId, Long userId);
}
