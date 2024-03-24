package com.example.mail.LogicalLayer.User.UserException.UserExceptionAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.mail.LogicalLayer.User.UserException.UserWithThisEmailAlreadyExistException;

@ControllerAdvice
public class UserWithThisEmailAlreadyExistAdvice {

    @ResponseBody
    @ExceptionHandler(UserWithThisEmailAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userWithThisEmailAlreadyExistHandler(UserWithThisEmailAlreadyExistException exception)
    {
        return exception.getMessage();
    }
}
