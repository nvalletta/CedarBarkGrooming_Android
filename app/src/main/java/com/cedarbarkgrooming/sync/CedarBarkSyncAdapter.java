package com.cedarbarkgrooming.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cedarbarkgrooming.R;
import com.cedarbarkgrooming.http.RestClient;
import com.cedarbarkgrooming.model.maps.Distance;
import com.cedarbarkgrooming.model.maps.Leg;
import com.cedarbarkgrooming.model.maps.MapsResponse;
import com.cedarbarkgrooming.model.maps.Route;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class CedarBarkSyncAdapter extends AbstractThreadedSyncAdapter {

    // 60 seconds * 60 = 1 hour
    public static final int SYNC_INTERVAL = 60*60;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    @Inject
    BehaviorSubject<String> mDistanceSubject;

    public CedarBarkSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        getInjector().inject(this);
    }

    public static void initialize(Context context) {
        getSyncAccount(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
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
        RestClient.getGoogleRestClient().getDistanceDataForLocation(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(mapsResponse -> mapsResponse != null)
                .map(this::getDistanceFromMapsResponse)
                .filter(distance -> distance != null)
                .subscribe(
                        (distance) -> {
                            Log.w("HomePresenter", distance.text);
                            mDistanceSubject.onNext(distance.text);
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

    private static Account getSyncAccount(Context context) {
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name),
                context.getString(R.string.sync_account_type));

        // If password doesn't exist, the account doesn't exist
        if (accountManager.getPassword(newAccount) == null) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            onAccountCreated(newAccount, context);
        }

        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        CedarBarkSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_provider_authority), true);
        syncImmediately(context);
    }

    private static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_provider_authority);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder()
                    .syncPeriodic(syncInterval, flexTime)
                    .setSyncAdapter(account, authority)
                    .setExtras(new Bundle())
                    .build();
            ContentResolver.requestSync(request);
        } else {
            // if we ever lower our API level:
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

        Account account = getSyncAccount(context);
        ContentResolver.requestSync(account,
                context.getString(R.string.content_provider_authority), bundle);
    }

}
