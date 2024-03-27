package com.example.mail.DatabaseLayer.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    public Log() {
        this.createdOn = LocalDateTime.now();
    }

    public Log(User user, String type) {
        this.user = user;
        this.type = type;
        this.createdOn = LocalDateTime.now();
    }

    public Long getId() {
       return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public String getType() {
        return this.type;
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
