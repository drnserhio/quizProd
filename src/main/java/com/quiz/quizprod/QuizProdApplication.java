package com.quiz.quizprod;

import com.quiz.quizprod.dao.QuestionDao;
import com.quiz.quizprod.dao.QuizDao;
import com.quiz.quizprod.dao.UserDao;
import com.quiz.quizprod.model.impl.Question;
import com.quiz.quizprod.model.impl.Quiz;
import com.quiz.quizprod.model.impl.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class QuizProdApplication {



    public static void main(String[] args) {
        SpringApplication.run(QuizProdApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserDao userDao, QuizDao quizDao, QuestionDao questionDao) {
        return args -> {

            Question question = new Question("How count methods has class object ?", "11", "22", "4");
            Question question1 = new Question("Abstract class has constructor ?", "yes", "not", "i don't know");
            Question question2 = new Question("What's this record ?", "constructor with final fields", "final class", "constructor");

            Question question3 = new Question("Human has 34 teeth ?", "yes", "no", "i don't know");
            Question question4 = new Question("Human has 620 muscules ?", "no", "yes", "i don't know");
            Question question5 = new Question("Human has 2 legs?", "yes", "no", "i don't know");
            Question question6 = new Question("24 - 10 = ?", "14", "10", "3");

            Question question7 = new Question("100 - 1 = ?", "99", "101", "100");
            Question question8 = new Question("10 / 2 = ?", "5", "2", "11");

            Question save = questionDao.save(question);
            System.out.println(save);
            Question save1 = questionDao.save(question1);
            Question save2 = questionDao.save(question2);
            Question save3 = questionDao.save(question3);
            Question save4 = questionDao.save(question4);
            Question save5 = questionDao.save(question5);
            Question save6 = questionDao.save(question6);
            Question save7 = questionDao.save(question7);
            Question save8 = questionDao.save(question8);

            Quiz quizIT = new Quiz("IT", "Theme about programming language.", new ArrayList<>());
            Quiz quizBiology = new Quiz("Biology", "Theme about anatomy human.", new ArrayList<>());
            Quiz quizMath = new Quiz("Mathemtic", "Theme about math 2 class", new ArrayList<>());

            Quiz quizSave = quizDao.save(quizIT);
            Quiz quizSave1 = quizDao.save(quizBiology);
            Quiz quizSave2 = quizDao.save(quizMath);

            quizDao.creatQuizWithQuestion(quizSave.getId(), save.getId(), save1.getId(), save2.getId());
            quizDao.creatQuizWithQuestion(quizSave1.getId(), save3.getId(), save4.getId(), save5.getId());
            quizDao.creatQuizWithQuestion(quizSave2.getId(), save6.getId(), save7.getId(), save8.getId());



            if (userDao.getUserByUsername("joy") != null) {
                User joy = userDao.getUserByUsername("joy");
                userDao.deleteByUsername("joy");
                userDao.deleteByUsername("jack");
            }
            User admin = new User();
            admin.setUsername("joy");
            admin.setPassword("5600");
            User user = new User();
            user.setUsername("jack");
            user.setPassword("5600");
            userDao.registerAccountAdmin(admin);
            userDao.registerAccount(user);
        };
    }


}
