package com.cedarbarkgrooming.ui.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cedarbarkgrooming.CedarBarkGroomingApplication;
import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.model.reminders.Reminder;
import com.cedarbarkgrooming.ui.BaseActivity;
import com.cedarbarkgrooming.ui.Presenter;
import com.cedarbarkgrooming.ui.reminders.RemindersActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.subjects.PublishSubject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class HomeActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final Uri CEDAR_BARK_URI = Uri.parse("https://www.google.com/maps/place/298+N+900+W,+Cedar+City,+UT+84721/@37.6825876,-113.0769967,17z/data=!3m1!4b1!4m5!3m4!1s0x80b56198c2942025:0xaec69677c68e45f0!8m2!3d37.6825876!4d-113.074808");
    private static final int LOADER_ID = 0x01;

    private static final int LOCATION_REQUEST = 1337;
    private static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    List<Reminder> mReminders;

    @Inject
    PublishSubject<Location> mCurrentUserLocation;

    Subscription mCurrentLocationSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getPresenter().setPresentedView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        mHomePresenter.setPresentedView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHomePresenter.onResume();
        subscribeToCurrentUserLocation();
        checkLocationAccess();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mCurrentLocationSubscription && !mCurrentLocationSubscription.isUnsubscribed()) {
            mCurrentLocationSubscription.unsubscribe();
        }
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
        return mHomePresenter.getReminderLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mHomePresenter.onLoadFinished(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no-op
    }

    private void subscribeToCurrentUserLocation() {
        mCurrentLocationSubscription =
            mCurrentUserLocation
                .distinctUntilChanged()
                .subscribe(
                    (location) -> {
                        // todo: populate view
                        mHomePresenter.discoverTimeToReachCedarBark(location);
                        Log.d("HomeActivity", "" + location);
                    },
                    (error) -> {
                        Log.e("HomeActivity", "Error getting user location.");
                    }
                );
    }

    private void checkLocationAccess() {
        if (hasLocationAccess()) {
            mHomePresenter.updateDistanceToCedarBark();
        } else {
            askUserForLocationPermission();
        }
    }

    @TargetApi(23)
    private void askUserForLocationPermission() {
        requestPermissions(LOCATION_PERMISSIONS, LOCATION_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mHomePresenter.updateDistanceToCedarBark();
    }

    private boolean hasLocationAccess() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;
    }
}
