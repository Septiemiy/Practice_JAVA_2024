package com.example.mail.LogicalLayer.Letter.LetterException;

public class LetterIsValidCronException extends RuntimeException {

    public LetterIsValidCronException(String cronException)
    {
        super("{\"error\": \"Invalid cron expression " + cronException + "\"}");
    }
}
