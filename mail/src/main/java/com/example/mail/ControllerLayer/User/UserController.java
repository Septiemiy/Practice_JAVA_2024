package com.example.mail.ControllerLayer.User;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mail.DatabaseLayer.Interfaces.UserRepository;
import com.example.mail.DatabaseLayer.Model.User;
import com.example.mail.LogicalLayer.User.UserLogical;
import com.example.mail.LogicalLayer.User.UserException.UserEmailNullOrEmptyException;
import com.example.mail.LogicalLayer.User.UserException.UserIsValidEmailException;
import com.example.mail.LogicalLayer.User.UserException.UserNotFoundException;
import com.example.mail.LogicalLayer.User.UserException.UserUsernameNullOrEmptyException;
import com.example.mail.LogicalLayer.User.UserException.UserWithThisEmailAlreadyExistException;
import com.example.mail.LogicalLayer.User.UserException.UserWithThisUsernameAlreadyExistException;

@RestController
public class UserController {
    
    private final UserRepository repository;

    UserController(UserRepository repository)
    {
        this.repository = repository;
    }

    @PostMapping("/create_user")
    ResponseEntity<Object> newUser(@RequestBody User newUser)
    {
        try
        {
            if(UserLogical.isNameNotNullNotEmpty(newUser.getUsername())) {
                return ResponseEntity.badRequest().body( new UserUsernameNullOrEmptyException());
            }
            
            if(UserLogical.isEmailNotNullNotEmpty(newUser.getEmail())) {
                return ResponseEntity.badRequest().body( new UserEmailNullOrEmptyException());
            }

            if(!UserLogical.isValidEmail(newUser.getEmail())) {
                return ResponseEntity.badRequest().body( new UserIsValidEmailException());
            }
        
            if(repository.findByUsername(newUser.getUsername()) != null) {
                throw new IllegalArgumentException( new UserWithThisUsernameAlreadyExistException());
            }

            if(repository.findByEmail(newUser.getEmail()) != null) {
                throw new IllegalArgumentException( new UserWithThisEmailAlreadyExistException());
            }

            return ResponseEntity.ok(repository.save(newUser));
        }
        catch(IllegalArgumentException exception)
        {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/update_user/{id}")
    ResponseEntity<Object> updateUser(@RequestBody User newUser, @PathVariable Long id) {
        
        if(UserLogical.isNameNotNullNotEmpty(newUser.getUsername())) {
            return ResponseEntity.badRequest().body( new UserUsernameNullOrEmptyException());
        }
        
        if(UserLogical.isEmailNotNullNotEmpty(newUser.getEmail())) {
            return ResponseEntity.badRequest().body( new UserEmailNullOrEmptyException());
        }

        if(!UserLogical.isValidEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body( new UserIsValidEmailException());
        }

        return ResponseEntity.ok(repository.findById(id)
            .map(user -> {
                user.setUsername(newUser.getUsername());
                user.setEmail(newUser.getEmail());
                return repository.save(user);
      })
      .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @PutMapping("/update_user/username/{id}")
    ResponseEntity<Object> updateUserUsernam(@RequestBody String newUsername, @PathVariable Long id) {
        
        if(UserLogical.isNameNotNullNotEmpty(newUsername)) {
            return ResponseEntity.badRequest().body( new UserUsernameNullOrEmptyException());
        }

        return ResponseEntity.ok(repository.findById(id)
            .map(user -> {
                user.setUsername(newUsername);
                return repository.save(user);
      })
      .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @PutMapping("/update_user/email/{id}")
    ResponseEntity<Object> updateUserEmail(@RequestBody String newEmail, @PathVariable Long id) {
        
        if(UserLogical.isEmailNotNullNotEmpty(newEmail)) {
            return ResponseEntity.badRequest().body( new UserEmailNullOrEmptyException());
        }

        if(!UserLogical.isValidEmail(newEmail)) {
            return ResponseEntity.badRequest().body( new UserIsValidEmailException());
        }

        return ResponseEntity.ok(repository.findById(id)
            .map(user -> {
                user.setEmail(newEmail);
                return repository.save(user);
      })
      .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @DeleteMapping("/delete_user/{id}")
    void deleteUser(@PathVariable Long id) {

        repository.deleteById(id);
    }

    @GetMapping("/get_users/{page}")
    List<User> getUsers(@PathVariable int page) {

        return repository.findAll(UserLogical.getPageable(page)).toList();
    }

    @GetMapping("/get_user/username/{username}")
    User getUserByUsername(@PathVariable String username) {

        return repository.findByUsername(username);
    }

    @GetMapping("/get_user/email/{email}")
    User getUserByEmail(@PathVariable String email) {

        return repository.findByEmail(email);
    }
}