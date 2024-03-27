package com.example.mail.LogicalLayer.Log.LogClass;

import java.time.LocalDateTime;

public class UserStatistics {

    private String username;
    private String email;
    private Count count;
    private LocalDateTime firstLetter;
    private LocalDateTime lastLetter;

    public UserStatistics(String username, String email, Count count, LocalDateTime firstLetter, LocalDateTime lastLetter) {
        this.username = username;
        this.email = email;
        this.count = count;
        this.firstLetter = firstLetter;
        this.lastLetter = lastLetter;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public Count getCount() {
        return this.count;
    }

    public LocalDateTime getFirstLetter() {
        return this.firstLetter;
    }

    public LocalDateTime getLastLetter() {
        return this.lastLetter;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public void setFirstLetter(LocalDateTime firstLetter) {
        this.firstLetter = firstLetter;
    }

    public void setLastLetter(LocalDateTime lastLetter) {
        this.lastLetter = lastLetter;
    }
}
