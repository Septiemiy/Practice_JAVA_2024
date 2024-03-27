package com.example.mail.DatabaseLayer.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mail.DatabaseLayer.Model.Log;

public interface LogRepository extends JpaRepository<Log, Long>{

}
