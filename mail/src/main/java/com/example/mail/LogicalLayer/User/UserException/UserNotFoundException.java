package com.example.mail.LogicalLayer.User.UserException;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(Long id) {
        super("{\"error\": \"Could not find user " + id + "\"}");
    }
}
