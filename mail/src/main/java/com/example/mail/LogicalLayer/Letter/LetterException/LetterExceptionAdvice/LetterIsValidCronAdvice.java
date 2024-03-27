package com.example.mail.LogicalLayer.Letter.LetterException.LetterExceptionAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.mail.LogicalLayer.Letter.LetterException.LetterIsValidCronException;

@ControllerAdvice
public class LetterIsValidCronAdvice {

    @ResponseBody
    @ExceptionHandler(LetterIsValidCronException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(LetterIsValidCronException exception)
    {
        return exception.getMessage();
    }
}
