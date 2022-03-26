package com.quiz.quizprod.service.impl;

import com.quiz.quizprod.dao.AnswerUserDao;
import com.quiz.quizprod.model.impl.AnswerUser;
import com.quiz.quizprod.service.AnwerUserService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnwerUserServiceImpl implements AnwerUserService {

    private final AnswerUserDao roomQuizDao;

    public AnwerUserServiceImpl(AnswerUserDao roomQuizDao) {
        this.roomQuizDao = roomQuizDao;
    }

    @Override
    public AnswerUser update(AnswerUser update) {
        return roomQuizDao.update(update);
    }

    @Override
    public AnswerUser save(AnswerUser save) throws Exception {
        return roomQuizDao.save(save);
    }

    @Override
    public AnswerUser findById(Long id) {
        return roomQuizDao.findById(id);
    }

    @Override
    public List<AnswerUser> findAll() {
        return roomQuizDao.findAll();
    }

    @Override
    public ResponseTable<AnswerUser> findAll(RequestTable requestTable) {
        return roomQuizDao.findAll(requestTable);
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        return roomQuizDao.deleteById(id);
    }
}
