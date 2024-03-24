package com.example.mail.LogicalLayer.User.UserException.UserExceptionAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.mail.LogicalLayer.User.UserException.UserUsernameNullOrEmptyException;

@ControllerAdvice
public class UserUsernameNullOrEmptyAdvice {

    @ResponseBody
    @ExceptionHandler(UserUsernameNullOrEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userUsernameNullOrEmptyHandler(UserUsernameNullOrEmptyException exception)
    {
        return exception.getMessage();
    }
}
