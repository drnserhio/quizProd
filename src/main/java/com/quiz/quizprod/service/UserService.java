package com.quiz.quizprod.service;

import com.quiz.quizprod.exception.domain.PasswordInvalidException;
import com.quiz.quizprod.exception.domain.QuizExistsException;
import com.quiz.quizprod.exception.domain.UserExistsException;
import com.quiz.quizprod.exception.domain.UsernameExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends BaseService<User>, UserDetailsService {

    User registerAccount(User user) throws UserExistsException, PasswordInvalidException, UsernameExistsException;
    User getUserByUsername(String username);



    List<Quiz> getFreeQuizByUserId(Long userId);

    boolean answerTheQuestion(String quizId, String username, String answer, Question question) throws QuizExistsException;

    boolean insertQuizToUserAfterTest(Long quizId, Long userId);
}
