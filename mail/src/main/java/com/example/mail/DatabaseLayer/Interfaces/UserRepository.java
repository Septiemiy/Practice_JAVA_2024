package com.example.mail.DatabaseLayer.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mail.DatabaseLayer.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
