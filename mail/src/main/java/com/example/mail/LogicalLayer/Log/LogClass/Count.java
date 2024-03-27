package com.example.mail.LogicalLayer.Log.LogClass;

public class Count {

    private int rest;
    private int cron;

    public Count(int rest, int cron)
    {
        this.rest = rest;
        this.cron = cron;
    }

    public int getRest() {
        return this.rest;
    }

    public int getCron() {
        return this.cron;
    }

    void setRest(int rest) {
        this.rest = rest;
    }

    void setCron(int cron) {
        this.cron = cron;
    }
}
