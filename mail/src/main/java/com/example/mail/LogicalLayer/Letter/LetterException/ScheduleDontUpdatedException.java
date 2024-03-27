package com.example.mail.LogicalLayer.Letter.LetterException;

public class ScheduleDontUpdatedException extends RuntimeException {

    public ScheduleDontUpdatedException()
    {
        super("{\"error\": \"Schedule dont updated\"}");
    }
}
