package com.cedarbarkgrooming.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.cedarbarkgrooming.data.reminders.ReminderContentProvider;
import com.cedarbarkgrooming.http.RestClient;
import com.cedarbarkgrooming.model.maps.Distance;
import com.cedarbarkgrooming.model.maps.Leg;
import com.cedarbarkgrooming.model.maps.MapsResponse;
import com.cedarbarkgrooming.model.maps.Route;
import com.cedarbarkgrooming.model.reminders.Reminder;
import com.cedarbarkgrooming.model.weather.CedarBarkGroomingWeather;
import com.cedarbarkgrooming.ui.Presenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

/**
 * Created by Nora on 5/7/2016.
 */
public class HomePresenter extends Presenter {

    private static final String ERROR_LOAD_REMINDERS = "Sorry! We were unable to load your reminders at this time.";
    private static final int HOURS_BETWEEN_WEATHER_REQUESTS = 1;
    private static final String STANDARD_DATE_FORMAT = "EEE MMMM dd hh:mm:ss z yyyy";

    @Inject
    List<Reminder> mReminders;

    @Nullable
    Subscription mWeatherSubscription;

    @Nullable
    CedarBarkGroomingWeather mCedarBarkGroomingWeather;

    boolean mIsUpdating;

    public HomePresenter() {
        getInjector().inject(this);
    }

    public void onViewImageGalleryClick() {
        // todo: open image gallery
    }

    public void onResume() {
        if (!mIsUpdating) {
            checkForWeatherUpdate();
        }
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

    public void updateDistanceToCedarBark() {
        Context context = mPresentedView.getViewContext();
        if (null == context) return;

        try {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Location currentUserLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (null !=currentUserLocation) {
                discoverTimeToReachCedarBark(currentUserLocation);
            }
        } catch (SecurityException ex) {
            Log.e("HomePresenter", "Error: couldn't calculate distance: " + ex.getMessage());
        }
    }

    public void discoverTimeToReachCedarBark(@Nullable Location userLocation) {
        if (null == userLocation) return;
        String position = userLocation.getLatitude() + "," + userLocation.getLongitude();
        mPresentedView.hideDistanceText();
        RestClient.getGoogleRestClient().getDistanceDataForLocation(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(mapsResponse -> mapsResponse != null)
                .map(this::getDistanceFromMapsResponse)
                .filter(distance -> distance != null)
                .subscribe(
                        (distance) -> {
                            Log.w("HomePresenter", distance.text);
                            mPresentedView.displayDistanceInformation(distance);
                        },
                        (error) -> {
                            Log.e("HomeActivity", "Oops! Couldn't retrieve distance data..." + error);
                        }
                );
    }

    @Nullable
    private Distance getDistanceFromMapsResponse(@NonNull MapsResponse response) {
        if (response.routes != null && response.routes.size() > 0) {
            Route route = response.routes.get(0);
            if (route.legs != null && route.legs.size() > 0) {
                Leg leg = route.legs.get(0);
                return leg.distance;
            }
        }
        return null;
    }

    private void checkForWeatherUpdate() {
        if (null == mCedarBarkGroomingWeather) {
            requestWeatherUpdate();
        } else {
            if (mCedarBarkGroomingWeather.enoughTimeHasPassedSinceLastUpdate(HOURS_BETWEEN_WEATHER_REQUESTS)) {
                requestWeatherUpdate();
            }
        }
    }

    private void requestWeatherUpdate() {
        mIsUpdating = true;
        mPresentedView.hideWeatherText();
        mWeatherSubscription = RestClient.getOpenWeatherRestClient().getWeatherDataForCity("Cedar City,us")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(weatherResponse1 -> (weatherResponse1 != null && weatherResponse1.weather.size() > 0))
                .subscribe(
                        (weatherResponse) -> {
                            String description = weatherResponse.weather.get(0).description;
                            double currentTemp = weatherResponse.main.temp;
                            mCedarBarkGroomingWeather = new CedarBarkGroomingWeather(description, currentTemp);
                            // todo: update views
                        },
                        (error) -> Log.e("HomePresenter", "Oops! Couldn't retrieve weather data..." + error),
                        () -> mIsUpdating = false
                );
    }

}
