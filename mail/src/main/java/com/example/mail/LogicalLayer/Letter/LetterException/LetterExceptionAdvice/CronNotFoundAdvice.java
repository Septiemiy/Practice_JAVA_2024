package com.example.mail.LogicalLayer.Letter.LetterException.LetterExceptionAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.mail.LogicalLayer.Letter.LetterException.CronNotFoundException;

@ControllerAdvice
public class CronNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CronNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(CronNotFoundException exception)
    {
        return exception.getMessage();
    }
}
