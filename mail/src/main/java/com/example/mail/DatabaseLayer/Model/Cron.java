package com.example.mail.DatabaseLayer.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cron")
public class Cron {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    Cron() {
        this.createdOn = LocalDateTime.now();
    }

    Cron(String expression) {
        this.expression = expression;
        this.createdOn = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public String getExpression() {
        return this.expression;
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
