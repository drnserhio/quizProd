package com.quiz.quizprod.dao;

import com.quiz.quizprod.model.impl.DefaultEntity;
import com.quiz.quizprod.table.RequestTable;
import com.quiz.quizprod.table.ResponseTable;

import java.util.List;

public interface BaseDao <T extends DefaultEntity> {

    T update(T update);
    T save(T save) throws Exception;
    T findById(Long id);

    List<T> findAll();

    ResponseTable<T> findAll(RequestTable requestTable);
    boolean deleteById(Long id) throws Exception;
    boolean existsById(Long id);
}
