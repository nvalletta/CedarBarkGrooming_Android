package com.cedarbarkgrooming.model.reminders;

import java.util.Date;

/**
 * Created by Nora on 5/14/2016.
 */
public class Reminder {

    private String title;
    private Date date;

    public Reminder(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
