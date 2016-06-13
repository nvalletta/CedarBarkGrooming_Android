package com.cedarbarkgrooming.ui.reminders;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.cedarbarkgrooming.data.reminders.ReminderContentProvider;
import com.cedarbarkgrooming.model.reminders.Reminder;
import com.cedarbarkgrooming.ui.PresentedView;
import com.cedarbarkgrooming.ui.Presenter;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/14/2016.
 */
public class CreateReminderPresenter extends Presenter<PresentedView> {

    private static final String ERROR_ADDING_REMINDER = "Sorry! A problem occurred while adding your reminder.";

    private static final String WARNING_PLEASE_ADD_TITLE = "Please add a name for your reminder.";
    private static final String WARNING_PLEASE_CHOOSE_DATE = "Please choose a future date for your reminder.";

    @Inject
    List<Reminder> mReminders;

    public CreateReminderPresenter() {
        getInjector().inject(this);
    }

    public void createReminder(@NonNull String title, @NonNull Date date) {
        if (null == mPresentedView) return;
        if (!validateInput(title, date)) return;

        ContentValues values = new ContentValues();
        values.put(ReminderContentProvider.TITLE, title);
        values.put(ReminderContentProvider.DATE, date.toString());

        ContentResolver contentResolver = getContentResolver();
        if (null != contentResolver) {
            try {
                mPresentedView.getViewContext().getContentResolver().insert(ReminderContentProvider.CONTENT_URI, values);
                mReminders.add(new Reminder(title, date));
            } catch(Exception e) {
                mPresentedView.showError(ERROR_ADDING_REMINDER);
            }
        } else {
            mPresentedView.showError(ERROR_ADDING_REMINDER);
        }
        mPresentedView.dismiss();
    }

    private boolean validateInput(@NonNull String title, @NonNull Date date) {
        if (title.isEmpty()) {
            mPresentedView.showError(WARNING_PLEASE_ADD_TITLE);
            return false;
        }
        if (date.before(new Date())) {
            mPresentedView.showError(WARNING_PLEASE_CHOOSE_DATE);
            return false;
        }
        return true;
    }

}
