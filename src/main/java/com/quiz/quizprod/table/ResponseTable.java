package com.quiz.quizprod.table;

import com.quiz.quizprod.model.impl.DefaultEntity;

import java.util.List;


public record ResponseTable<T extends DefaultEntity>(List<T> content,
                                                     String sort,
                                                     String column,
                                                     int currentPage,
                                                     int totalPage,
                                                     int allItems) {

}
