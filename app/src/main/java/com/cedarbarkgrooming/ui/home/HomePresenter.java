package com.cedarbarkgrooming.ui.home;

import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.cedarbarkgrooming.data.reminders.ReminderContentProvider;
import com.cedarbarkgrooming.model.reminders.Reminder;
import com.cedarbarkgrooming.ui.Presenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/7/2016.
 */
public class HomePresenter extends Presenter<HomeView> {

    private static final String ERROR_LOAD_REMINDERS = "Sorry! We were unable to load your reminders at this time.";
    private static final String STANDARD_DATE_FORMAT = "EEE MMMM dd hh:mm:ss z yyyy";

    @Inject
    List<Reminder> mReminders;

    public HomePresenter() {
        getInjector().inject(this);
    }

    public Loader<Cursor> getReminderLoader() {
        String[] projection = new String[2];
        projection[0] = ReminderContentProvider.TITLE;
        projection[1] = ReminderContentProvider.DATE;
        return new CursorLoader(
                mPresentedView.getViewContext(),   // Parent activity context
                ReminderContentProvider.CONTENT_URI,        // Table to query
                projection,     // Projection to return
                null,            // No selection clause
                null,            // No selection arguments
                null             // Default sort order
        );
    }

    public void onLoadFinished(Cursor cursor) {
        cursor.moveToFirst();
        mReminders.clear();
        while (!cursor.isAfterLast()) {
            try {
                DateFormat df = new SimpleDateFormat(STANDARD_DATE_FORMAT, Locale.US);
                Date date = df.parse(cursor.getString(1));
                Reminder reminder = new Reminder(cursor.getString(0), date);
                mReminders.add(reminder);
            } catch (Exception e) {
                Log.e("HomeActivity", e.getMessage());
                mPresentedView.showError(ERROR_LOAD_REMINDERS);
                return;
            }
            cursor.moveToNext();
        }
    }

}
