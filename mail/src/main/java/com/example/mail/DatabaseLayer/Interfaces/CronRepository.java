package com.example.mail.DatabaseLayer.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mail.DatabaseLayer.Model.Cron;

public interface CronRepository extends JpaRepository<Cron, Long> {
    
}
