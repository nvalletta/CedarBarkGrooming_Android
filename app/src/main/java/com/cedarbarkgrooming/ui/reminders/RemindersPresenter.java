package com.cedarbarkgrooming.ui.reminders;

import android.content.ContentResolver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cedarbarkgrooming.data.reminders.ReminderContentProvider;
import com.cedarbarkgrooming.model.Reminder;
import com.cedarbarkgrooming.ui.Presenter;

import java.util.List;

import javax.inject.Inject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class RemindersPresenter extends Presenter {

    private static final String ERROR_DELETING_REMINDER = "Uh oh, a problem occurred while deleting your Reminder. Please try again later.";

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

    public void onReminderDeleted(@NonNull Reminder reminder) {
        ContentResolver contentResolver = getContentResolver();
        if (null != contentResolver) {
            try {
                String where = ReminderContentProvider.TITLE + " = '" + reminder.getTitle()
                        + "' AND " + ReminderContentProvider.DATE + " = '" + reminder.getDate().toString() + "'";
                contentResolver.delete(ReminderContentProvider.CONTENT_URI, where, null);
                mReminders.remove(reminder);
                if (null != mRemindersChangeListener) {
                    mRemindersChangeListener.onRemindersChanged();
                }
            } catch (Exception e) {
                mPresentedView.showError(ERROR_DELETING_REMINDER);
            }
        } else {
            mPresentedView.showError(ERROR_DELETING_REMINDER);
        }
    }

}
