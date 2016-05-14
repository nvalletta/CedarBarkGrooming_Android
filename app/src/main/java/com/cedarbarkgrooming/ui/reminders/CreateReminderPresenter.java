package com.cedarbarkgrooming.ui.reminders;

import com.cedarbarkgrooming.model.Reminder;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class CreateReminderPresenter {

    @Inject
    List<Reminder> mReminders;

    public CreateReminderPresenter() {
        getInjector().inject(this);
    }

    public void createReminder(String title, Date date) {
        mReminders.add(new Reminder(title, date));
    }

}
