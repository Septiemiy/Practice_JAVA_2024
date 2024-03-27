package com.example.mail.LogicalLayer.Letter.LetterException;

public class CronNotFoundException extends RuntimeException {

    public CronNotFoundException(Long cron_id)
    {
        super("{\"error\": \"Could not found cron " + cron_id + "\"}");
    }
}
