package com.example.mail.LogicalLayer.User.UserException;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException() {
        super("{\"error\": \"Could not find user\"}");
    }

    public UserNotFoundException(Long id) {
        super("{\"error\": \"Could not find user " + id + "\"}");
    }
}
