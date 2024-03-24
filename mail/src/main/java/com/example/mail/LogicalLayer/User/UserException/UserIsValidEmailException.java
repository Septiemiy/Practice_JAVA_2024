package com.example.mail.LogicalLayer.User.UserException;

public class UserIsValidEmailException extends RuntimeException {

    public UserIsValidEmailException() {
        super("{\"error\": \"Email is not valid\"}");
    }
}
