package com.example.mail.LogicalLayer.User.UserException;

public class UserWithThisEmailAlreadyExistException extends RuntimeException {

    public UserWithThisEmailAlreadyExistException() {
        super("{\"error\": \"User with this email already exist\"}");
    }
}
