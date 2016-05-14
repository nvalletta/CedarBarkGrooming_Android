package com.cedarbarkgrooming.ui.reminders;

import android.support.annotation.Nullable;

import com.cedarbarkgrooming.model.Reminder;

import java.util.List;

import javax.inject.Inject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class RemindersPresenter {

    @Inject
    List<Reminder> mReminders;

    @Nullable
    RemindersChangeListener mRemindersChangeListener;

    public RemindersPresenter() {
        getInjector().inject(this);
    }

    public void setRemindersChangeListener(@Nullable RemindersChangeListener listener) {
        mRemindersChangeListener = listener;
    }

    public void onReminderDeleted(Reminder reminder) {
        mReminders.remove(reminder);
        if (null != mRemindersChangeListener) {
            mRemindersChangeListener.onRemindersChanged();
        }
    }
}
