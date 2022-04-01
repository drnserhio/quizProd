package com.quiz.quizprod.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.quiz.quizprod.exception.domain.*;
import com.quiz.quizprod.model.response.HttpResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Objects;

import static com.quiz.quizprod.constant.HandlingExceptionConstant.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling {

    @PostMapping(ERROR_PATH_HANDLER)
    public ResponseEntity<HttpResponseBody> notfound404() {
        return createHttpResponseBody(NOT_FOUND, IS_NOT_MAPPING_THIS_URL);
    }


    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponseBody> accountIsDisabled() {
        return createHttpResponseBody(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponseBody> methodIsNotAllowed(HttpRequestMethodNotSupportedException e) {
        HttpMethod httpMethod = Objects.requireNonNull(e.getSupportedHttpMethods().iterator().next());
        return createHttpResponseBody(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, httpMethod));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponseBody> accountIsLocked() {
        return createHttpResponseBody(UNAUTHORIZED, ACCOUNT_LOCKED);
    }
    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<HttpResponseBody> passwordIsNotValid() {
        return createHttpResponseBody(UNAUTHORIZED, PASSWORD_IS_NOT_VALID);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponseBody> accessDeniedException() {
        return createHttpResponseBody(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponseBody> badCredentilas() {
        return createHttpResponseBody(BAD_REQUEST, INCORRECT_CREDENTILAS);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponseBody> internalServerError() {
        return createHttpResponseBody(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponseBody> userNotFoundException(UserNotFoundException e) {
        return createHttpResponseBody(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponseBody> notFoundException(NoResultException e) {
        return createHttpResponseBody(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponseBody> iOException() {
        return createHttpResponseBody(INTERNAL_SERVER_ERROR, ERROR_PROCCESSING_FILE);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<HttpResponseBody> usernameExistsException(UsernameExistsException e) {
        return createHttpResponseBody(BAD_REQUEST, e.getMessage());
    }


    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponseBody> tokenExpiredException(TokenExpiredException e) {
        return createHttpResponseBody(UNAUTHORIZED, e.getMessage());
    }


    @ExceptionHandler(AnswerUserNotFoundException.class)
    public ResponseEntity<HttpResponseBody> answerNotFoundException(AnswerUserNotFoundException e) {
        return createHttpResponseBody(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(QuestionExistsException.class)
    public ResponseEntity<HttpResponseBody> questionExistsException(QuizExistsException e) {
        return createHttpResponseBody(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<HttpResponseBody> questionNotFoundException(QuestionNotFoundException e) {
        return createHttpResponseBody(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(QuizExistsException.class)
    public ResponseEntity<HttpResponseBody> quizExistsException(QuizExistsException e) {
        return createHttpResponseBody(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<HttpResponseBody> quizNotFoundException(QuizNotFoundException e) {
        return createHttpResponseBody(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<HttpResponseBody> quizNotFoundException(UserExistsException e) {
        return createHttpResponseBody(BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<HttpResponseBody> createHttpResponseBody(HttpStatus status, String message) {
        return new ResponseEntity<>(
                new HttpResponseBody(
                        status.value(),
                        status,
                        status.getReasonPhrase().toUpperCase(),
                        message.toUpperCase()),
                status
        );
    }


}
