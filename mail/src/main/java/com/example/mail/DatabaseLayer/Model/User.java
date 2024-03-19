package com.example.mail.DatabaseLayer.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/*User entity for database*/

@Entity
@Table(name = "UserMail")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String username;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String email;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private LocalDateTime createdOn;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.createdOn = LocalDateTime.now();
    }

}
