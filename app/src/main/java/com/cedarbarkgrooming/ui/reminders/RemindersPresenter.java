package com.cedarbarkgrooming.ui.reminders;

import com.cedarbarkgrooming.model.Reminder;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Nora on 5/14/2016.
 */
public class RemindersPresenter {

    @Inject
    List<Reminder> mReminders;

    public RemindersPresenter() {

    }

}
