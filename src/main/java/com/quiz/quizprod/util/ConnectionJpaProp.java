package com.quiz.quizprod.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ConnectionJpaProp {

    @Value("${spring.datasource.driver-class-name}")
    private String nameDriverClass;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

    @Value("${spring.jpa.show-sql}")
    private String show_sql;


    @Value("${spring.jpa.hibernate.jdbc.max_size}")
    private String maxSize;

    @Value("${spring.jpa.hibernate.jdbc.min_size}")
    private String minSize;

    @Value("${spring.jpa.hibernate.jdbc.batch_size}")
    private String batchSize;

    @Value("${spring.jpa.hibernate.jdbc.fetch_size}")
    private String fetchSize;

}
