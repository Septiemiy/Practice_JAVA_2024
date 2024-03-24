package com.example.mail.DatabaseLayer.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mail.DatabaseLayer.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    @Query(value = "SELECT * FROM USER_MAIL WHERE USERNAME = ?1", nativeQuery = true)
    User findByUsername(String username);
    @Query(value = "SELECT * FROM USER_MAIL WHERE EMAIL = ?1", nativeQuery = true)
    User findByEmail(String email);
}
