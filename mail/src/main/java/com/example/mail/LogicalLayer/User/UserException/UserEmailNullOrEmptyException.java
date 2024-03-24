package com.example.mail.LogicalLayer.User.UserException;

public class UserEmailNullOrEmptyException extends RuntimeException{

    public UserEmailNullOrEmptyException() {
        super("{\"error\": \"Null or empty email\"}");
    }
}
