package com.quiz.quizprod.service.impl;

import com.quiz.quizprod.dao.UserDao;
import com.quiz.quizprod.exception.PasswordInvalidException;
import com.quiz.quizprod.exception.QuizExistsException;
import com.quiz.quizprod.exception.UserExistsException;
import com.quiz.quizprod.exception.UsernameExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.User;
import com.quiz.quizprod.model.principal.CustomUserPrincipal;
import com.quiz.quizprod.service.UserService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record UserServiceImpl(UserDao userDao) implements UserService {

    @Override
    public User update(User update) {
        return userDao.update(update);
    }

    @Override
    public User save(User save) throws Exception {
        return userDao.save(save);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public ResponseTable<User> findAll(RequestTable requestTable) {
        return userDao.findAll(requestTable);
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        return userDao.deleteById(id);
    }

    @Override
    public User registerAccount(User user)
            throws UserExistsException, PasswordInvalidException, UsernameExistsException {
        return userDao.registerAccount(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<Quiz> getFreeQuizByUserId(Long userId) {
        return userDao.getFreeQuizByUserId(userId);
    }

    @Override
    public boolean answerTheQuestion(String quizId, String username, String answer, Question question) throws QuizExistsException {
        return userDao.answerTheQuestion(quizId, username, answer, question);
    }

    @Override
    public boolean insertQuizToUserAfterTest(Long quizId, Long userId) {
      return userDao.insertQuizToUserAfterTest(quizId, userId);
    }


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserPrincipal(user);
    }
}
