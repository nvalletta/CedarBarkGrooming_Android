package com.cedarbarkgrooming.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cedarbarkgrooming.CedarBarkGroomingApplication;
import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.data.reminders.ReminderContentProvider;
import com.cedarbarkgrooming.model.Reminder;
import com.cedarbarkgrooming.ui.BaseActivity;
import com.cedarbarkgrooming.ui.Presenter;
import com.cedarbarkgrooming.ui.reminders.RemindersActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class HomeActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final Uri CEDAR_BARK_URI = Uri.parse("https://www.google.com/maps/place/298+N+900+W,+Cedar+City,+UT+84721/@37.6825876,-113.0769967,17z/data=!3m1!4b1!4m5!3m4!1s0x80b56198c2942025:0xaec69677c68e45f0!8m2!3d37.6825876!4d-113.074808");
    private static final int LOADER_ID = 0x01;

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    List<Reminder> mReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getPresenter().setPresentedView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        mHomePresenter.onPresentedViewCreated();
    }

    @NonNull
    @Override
    protected Presenter getPresenter() {
        return mHomePresenter;
    }

    @Override
    protected void inject() {
        getInjector().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_navigate)
    public void onNavigateClicked() {
        if (CedarBarkGroomingApplication.isAppInstalled("com.google.android.apps.maps")) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, CEDAR_BARK_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
    }

    @OnClick(R.id.button_description)
    public void onViewGalleryClicked() {
        mHomePresenter.onViewImageGalleryClick();
    }

    @OnClick(R.id.layout_reminders)
    public void onManageRemindersClicked() {
        startActivity(new Intent(this, RemindersActivity.class));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = new String[2];
        projection[0] = ReminderContentProvider.TITLE;
        projection[1] = ReminderContentProvider.DATE;
        return new CursorLoader(
                this,   // Parent activity context
                ReminderContentProvider.CONTENT_URI,        // Table to query
                projection,     // Projection to return
                null,            // No selection clause
                null,            // No selection arguments
                null             // Default sort order
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        mReminders.clear();
        while (!cursor.isAfterLast()) {
            try {
                DateFormat df = new SimpleDateFormat("EEE MMMM dd hh:mm:ss z yyyy", Locale.US);
                Date date = df.parse(cursor.getString(1));
                Reminder reminder = new Reminder(cursor.getString(0), date);
                mReminders.add(reminder);
            } catch (Exception e) {
                Log.e("HomeActivity", e.getMessage());
                showError("Sorry! We were unable to load your reminders at this time.");
                return;
            }
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no-op
    }
}
