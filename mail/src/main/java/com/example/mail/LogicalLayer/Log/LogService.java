package com.example.mail.LogicalLayer.Log;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.mail.DatabaseLayer.Interfaces.LogRepository;
import com.example.mail.DatabaseLayer.Interfaces.UserRepository;
import com.example.mail.DatabaseLayer.Model.Log;
import com.example.mail.DatabaseLayer.Model.User;
import com.example.mail.LogicalLayer.Log.LogClass.Count;
import com.example.mail.LogicalLayer.User.UserException.UserNotFoundException;

@Service
public class LogService {

    public static void log(UserRepository userRepository, LogRepository logRepository, String type, String email)
    {
        User user = userRepository.findByEmail(email);
        
        if(user != null)
        {
            Log log = new Log(user, type);
            logRepository.save(log);
        }
        else
        {
            new UserNotFoundException().printStackTrace();
        }
    }

    public static Count countRestCronLetter(Set<Log> logs) {
        int rest = 0;
        int cron = 0;

        for(Log log : logs)
        {
            if(log.getType() == "REST")
            {
                rest++;
            }
            else 
            {
                cron++;
            }
        }

        return new Count(rest, cron);
    }

    public static LocalDateTime getFirstLetterDateTime(Set<Log> logs)
    {
        LocalDateTime firstDateTime = null;

        for (Log log : logs) {
            LocalDateTime logDateTime = log.getCreatedOn();
            if (firstDateTime == null || logDateTime.isBefore(firstDateTime)) {
                firstDateTime = logDateTime;
            }
        }

        return firstDateTime;
    }

    public static LocalDateTime getLastLetterDateTime(Set<Log> logs)
    {
        LocalDateTime lastDateTime = null;

        for (Log log : logs) {
            LocalDateTime logDateTime = log.getCreatedOn();
            if (lastDateTime == null || logDateTime.isAfter(lastDateTime)) {
                lastDateTime = logDateTime;
            }
        }

        return lastDateTime;
    }
}