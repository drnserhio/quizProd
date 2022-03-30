package com.quiz.quizprod.service.impl;

import com.quiz.quizprod.dao.AnswerUserDao;
import com.quiz.quizprod.model.impl.AnswerUser;
import com.quiz.quizprod.service.AnwerUserService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record AnwerUserServiceImpl(AnswerUserDao answersDao) implements AnwerUserService {

    @Override
    public AnswerUser update(AnswerUser update) {
        return answersDao.update(update);
    }

    @Override
    public AnswerUser save(AnswerUser save) throws Exception {
        return answersDao.save(save);
    }

    @Override
    public AnswerUser findById(Long id) {
        return answersDao.findById(id);
    }

    @Override
    public List<AnswerUser> findAll() {
        return answersDao.findAll();
    }

    @Override
    public ResponseTable<AnswerUser> findAll(RequestTable requestTable) {
        return answersDao.findAll(requestTable);
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        return answersDao.deleteById(id);
    }

    @Override
    public List<AnswerUser> getAllPassedTestByQuizId(String quizId, String username) {
        return answersDao.getAllPassedTestByQuizId(quizId, username);
    }

    @Override
    public boolean deleteAllAnswersIfCloseTest(String quizId, String username) {
        return answersDao.deleteAllAnswersIfCloseTest(quizId, username);
    }


}
