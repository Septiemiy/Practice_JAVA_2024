package com.example.mail.LogicalLayer.Letter.LetterClass;

import com.example.mail.DatabaseLayer.Model.Cron;

public class LetterAndCron {

    private Letter letter;
    private Cron cron;

    public Letter getLetter() {
        return letter;
    }

    public Cron getCron() {
        return cron;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public void setCron(Cron cron) {
        this.cron = cron;
    }
}
