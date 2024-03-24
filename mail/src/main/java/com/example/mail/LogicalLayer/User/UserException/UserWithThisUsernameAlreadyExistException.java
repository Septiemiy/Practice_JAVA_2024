package com.example.mail.LogicalLayer.User.UserException;

public class UserWithThisUsernameAlreadyExistException extends RuntimeException{

    public UserWithThisUsernameAlreadyExistException() {
        super("{\"error\": \"User with this username already exist\"");
    }
}
