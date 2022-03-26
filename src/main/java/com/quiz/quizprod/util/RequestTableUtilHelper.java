package com.quiz.quizprod.util;

import com.quiz.quizprod.table.RequestTable;
import lombok.experimental.UtilityClass;

import static org.apache.commons.lang.StringUtils.isEmpty;

@UtilityClass
public final class RequestTableUtilHelper {
    public final static String DEFAULT_SORT = "asc";
    public final static String DEFAULT_COLUMN = "id";
    public final static int DEFAULT_PAGE = 1;
    public final static int DEFAULT_SIZE = 10;

    public static void initialDefaultValue(RequestTable request) {
        if (isEmpty(String.valueOf(request.getPage()))) {
            request.setPage(DEFAULT_PAGE);
        }
        if (isEmpty(String.valueOf(request.getSize()))) {
            request.setSize(DEFAULT_SIZE);
        }
        if (isEmpty(request.getColumn())) {
            request.setColumn(DEFAULT_COLUMN);
        }
        if (isEmpty(request.getSort())) {
            request.setSort(DEFAULT_SORT);
        }
    }
}
