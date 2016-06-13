package com.cedarbarkgrooming.ui.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cedarbarkgrooming.CedarBarkGroomingApplication;
import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.model.maps.Distance;
import com.cedarbarkgrooming.model.weather.CedarBarkGroomingWeather;
import com.cedarbarkgrooming.sync.CedarBarkSyncAdapter;
import com.cedarbarkgrooming.ui.BaseActivity;
import com.cedarbarkgrooming.ui.gallery.GalleryActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class HomeActivity extends BaseActivity implements HomeView, LoaderManager.LoaderCallbacks<Cursor> {

    private static final Uri CEDAR_BARK_URI = Uri.parse("https://www.google.com/maps/place/298+N+900+W,+Cedar+City,+UT+84721/@37.6825876,-113.0769967,17z/data=!3m1!4b1!4m5!3m4!1s0x80b56198c2942025:0xaec69677c68e45f0!8m2!3d37.6825876!4d-113.074808");
    private static final int LOADER_ID = 0x01;
    private static final String KEY_PERMISSION_DENIED = "booleanPermissionDenied";

    private static final int LOCATION_REQUEST = 1337;
    private static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    BehaviorSubject<String> mDistanceSubject;

    @Inject
    BehaviorSubject<CedarBarkGroomingWeather> mWeatherSubject;

    @BindView(R.id.text_weather)
    TextView mTextWeather;

    @BindView(R.id.text_distance)
    TextView mTextDistance;

    Subscription mDistanceSubscription;

    Subscription mWeatherSubscription;

    boolean mDeniedLocationRequest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        if (null != savedInstanceState) {
            mDeniedLocationRequest = savedInstanceState.getBoolean(KEY_PERMISSION_DENIED, false);
        }
        mHomePresenter.setPresentedView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        mHomePresenter.setPresentedView(this);

        watchForDistanceUpdates();
        watchForWeatherUpdates();
        syncData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_PERMISSION_DENIED, mDeniedLocationRequest);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocationAccess();
        watchEvents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unwatchEvents();
    }

    private void watchEvents() {
        if (null == mDistanceSubscription || mDistanceSubscription.isUnsubscribed()) {
            watchForDistanceUpdates();
        }
        if (null == mWeatherSubscription || mWeatherSubscription.isUnsubscribed()) {
            watchForWeatherUpdates();
        }
    }

    private void unwatchEvents() {
        if (null != mDistanceSubscription && !mDistanceSubscription.isUnsubscribed()) {
            mDistanceSubscription.unsubscribe();
        }
        if (null != mWeatherSubscription && !mWeatherSubscription.isUnsubscribed()) {
            mWeatherSubscription.unsubscribe();
        }
    }

    @Override
    protected void inject() {
        getInjector().inject(this);
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
        startActivity(new Intent(this, GalleryActivity.class));
    }

    @OnClick(R.id.layout_reminders)
    public void onManageRemindersClicked() {
        //todo: in the future we'll be replacing these reminders with Google Calendar Reminders
//        startActivity(new Intent(this, RemindersActivity.class));
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

    private void syncData() {
        CedarBarkSyncAdapter.syncImmediately(this);
    }

    private void checkLocationAccess() {
        // If we already have location access, or if we've been denied that permission,
        // don't ask again - just initialize our sync adapter.
        if (hasLocationAccess() || mDeniedLocationRequest) {
            CedarBarkSyncAdapter.initialize(this);
        } else {
            askUserForLocationPermission();
        }
    }

    @TargetApi(23)
    private void askUserForLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(LOCATION_PERMISSIONS, LOCATION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i : grantResults) {
            if (i == PackageManager.PERMISSION_DENIED) {
                mDeniedLocationRequest = true;
            }
        }
        syncData();
    }

    private boolean hasLocationAccess() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;
    }

    private void watchForDistanceUpdates() {
        mDistanceSubscription = mDistanceSubject
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::populateDistanceView,
                        (error) -> {
                            Log.e("HomeActivity", "Error finding distance to Cedar Bark.");
                            mTextDistance.setText("");
                            hideDistanceText();
                        }
                );
    }

    private void watchForWeatherUpdates() {
        mWeatherSubscription = mWeatherSubject
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::populateWeatherView,
                        (error) -> {
                            Log.e("HomeActivity", "Error finding distance to Cedar Bark.");
                            mTextWeather.setText("");
                            hideWeatherText();
                        }
                );
    }

    private void populateDistanceView(String distance) {
        if (null == distance || distance.isEmpty()) {
            hideDistanceText();
        } else {
            mTextDistance.setText(getString(R.string.text_distance, distance));
            mTextDistance.setVisibility(View.VISIBLE);
        }
    }

    private void populateWeatherView(CedarBarkGroomingWeather weather) {
        if (null == weather) {
            hideDistanceText();
        } else {
            mTextWeather.setText(getString(R.string.text_weather,
                    weather.getCurrentTemperatureFahrenheit()));
            mTextWeather.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayDistanceInformation(@NonNull Distance distance) {
        String message = getString(R.string.text_distance, distance.text);
        mTextDistance.setText(message);
        mTextDistance.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayWeatherInformation(CedarBarkGroomingWeather weather) {
        String message = getString(R.string.text_weather, weather.getCurrentTemperatureFahrenheit());
        mTextWeather.setText(message);
        mTextWeather.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDistanceText() {
        mTextDistance.setVisibility(View.GONE);
    }

    @Override
    public void hideWeatherText() {
        mTextWeather.setVisibility(View.GONE);
    }
}
