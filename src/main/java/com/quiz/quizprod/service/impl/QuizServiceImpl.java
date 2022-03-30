package com.quiz.quizprod.service.impl;

import com.quiz.quizprod.dao.QuizDao;
import com.quiz.quizprod.exception.AnswerUserFoundException;
import com.quiz.quizprod.exception.QuizExistsException;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.service.QuizService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public Quiz update(Quiz update) {
        return quizDao.update(update);
    }

    @Override
    public Quiz save(Quiz save) throws Exception {
        return quizDao.save(save);
    }

    @Override
    public Quiz findById(Long id) {
        return quizDao.findById(id);
    }

    @Override
    public List<Quiz> findAll() {
        return quizDao.findAll();
    }

    @Override
    public ResponseTable<Quiz> findAll(RequestTable requestTable) {
        return quizDao.findAll(requestTable);
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        return quizDao.deleteById(id);
    }

    @Override
    public boolean creatQuizWithQuestion(Long quizId, Long... questionId)
            throws QuizExistsException, AnswerUserFoundException {
        return quizDao.creatQuizWithQuestion(quizId, questionId);
    }
}
