package com.example.mail.LogicalLayer.User.UserException;

public class UserUsernameNullOrEmptyException extends RuntimeException{

    public UserUsernameNullOrEmptyException() {
        super("{\"error\": \"Null or empty username\"}");
    }
}
