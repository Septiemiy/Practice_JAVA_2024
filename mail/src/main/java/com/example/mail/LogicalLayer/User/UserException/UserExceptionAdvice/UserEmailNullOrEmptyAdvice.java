package com.example.mail.LogicalLayer.User.UserException.UserExceptionAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.mail.LogicalLayer.User.UserException.UserEmailNullOrEmptyException;

@ControllerAdvice
public class UserEmailNullOrEmptyAdvice {
    
    @ResponseBody
    @ExceptionHandler(UserEmailNullOrEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userEmailNullOrEmptyHandler(UserEmailNullOrEmptyException exception)
    {
        return exception.getMessage();
    }
    
}
