package com.cedarbarkgrooming.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CedarBarkAuthenticatorService extends Service {

    private CedarBarkAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new CedarBarkAuthenticator(this);
        super.onCreate();
    }

    /**
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

}
