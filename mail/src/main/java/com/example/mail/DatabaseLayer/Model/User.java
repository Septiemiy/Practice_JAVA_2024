package com.example.mail.DatabaseLayer.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/*User entity for database*/

@Entity
@Table(name = "UserMail")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String username;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private LocalDateTime createdOn;

    User() {
        this.createdOn = LocalDateTime.now();
    }

    User(String username, String email) {
        this.username = username;
        this.email = email;
        this.createdOn = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}