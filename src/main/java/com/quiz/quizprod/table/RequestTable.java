package com.quiz.quizprod.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RequestTable {
    public String column;
    public String sort;
    public int page;
    public int size;
}
