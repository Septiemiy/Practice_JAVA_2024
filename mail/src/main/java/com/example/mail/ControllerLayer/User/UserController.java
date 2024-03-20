package com.example.mail.ControllerLayer.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mail.DatabaseLayer.Interfaces.UserRepository;
import com.example.mail.DatabaseLayer.Model.User;

@RestController
public class UserController {
    
    private final UserRepository repository;

    UserController(UserRepository repository)
    {
        this.repository = repository;
    }

    @PostMapping("/create_user")
    User newUser(@RequestBody User newUser)
    {
        return repository.save(newUser);
    }
}