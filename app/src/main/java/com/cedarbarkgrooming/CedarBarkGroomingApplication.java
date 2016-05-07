package com.cedarbarkgrooming;

import android.app.Application;

import com.cedarbarkgrooming.module.DaggerObjectComponent;
import com.cedarbarkgrooming.module.modules.ApplicationModule;

import static com.cedarbarkgrooming.module.ObjectGraph.setObjectComponent;

/**
 * Created by Nora on 5/7/2016.
 */
public class CedarBarkGroomingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setObjectComponent(DaggerObjectComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build());
    }
}
