package com.quiz.quizprod.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class CustomHttpResponse {
    @JsonFormat(pattern = "dd:MM:yyyy hh:mm:ss")
    private Date timeStamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;


    public CustomHttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        timeStamp = new Date();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }
}
