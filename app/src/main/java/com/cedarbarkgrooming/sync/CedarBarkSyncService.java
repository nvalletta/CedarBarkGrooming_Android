package com.cedarbarkgrooming.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class CedarBarkSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static CedarBarkSyncAdapter sCedarBarkSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("CedarBarkSyncService", "onCreate - CedarBarkSyncService");
        synchronized (sSyncAdapterLock) {
            if (null == sCedarBarkSyncAdapter) {
                sCedarBarkSyncAdapter = new CedarBarkSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sCedarBarkSyncAdapter.getSyncAdapterBinder();
    }
}
