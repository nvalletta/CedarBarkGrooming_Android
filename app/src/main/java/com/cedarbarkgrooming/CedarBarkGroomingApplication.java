package com.cedarbarkgrooming;

import android.app.Application;
import android.content.pm.PackageManager;

import com.cedarbarkgrooming.module.DaggerObjectComponent;
import com.cedarbarkgrooming.module.modules.ApplicationModule;

import static com.cedarbarkgrooming.module.ObjectGraph.setObjectComponent;

/**
 * Created by Nora on 5/7/2016.
 */
public class CedarBarkGroomingApplication extends Application {

    private static CedarBarkGroomingApplication sApplicationInstance;
    private static final String GOOGLE_API_KEY = "AIzaSyCdeHW8jWrm_MPNfyCiN_w9wORSrSfT0sg";

    public static CedarBarkGroomingApplication getApplication() {
        return sApplicationInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationInstance = this;
        setObjectComponent(DaggerObjectComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build());
    }

    public static boolean isAppInstalled(String uri) {
        PackageManager packageManager = getApplication().getPackageManager();
        boolean appIsInstalled = false;
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            appIsInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appIsInstalled = false;
        }
        return appIsInstalled;
    }

}
