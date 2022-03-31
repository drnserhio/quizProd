package com.quiz.quizprod.service.impl;

import com.quiz.quizprod.dao.QuestionDao;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.service.QuestionService;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question update(Question update) {
        return questionDao.update(update);
    }

    @Override
    public Question save(Question save) throws Exception {
        return questionDao.save(save);
    }

    @Override
    public Question findById(Long id) {
        return questionDao.findById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionDao.findAll();
    }

    @Override
    public ResponseTable<Question> findAll(RequestTable requestTable) {
        return questionDao.findAll(requestTable);
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        return questionDao.deleteById(id);
    }

    @Override
    public List<Question> getAllQuestionWithoutInQuiz(String quizId) {
        return questionDao.getAllQuestionWithoutInQuiz(quizId);
    }
}
