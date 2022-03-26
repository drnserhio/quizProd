package com.quiz.quizprod.exception;

public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }
}
