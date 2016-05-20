package com.cedarbarkgrooming.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import static com.cedarbarkgrooming.module.ObjectGraph.getInjector;

public class WidgetService extends RemoteViewsService {

    @Override
    public void onCreate() {
        super.onCreate();
        getInjector().inject(this);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new ReminderWidgetViewsFactory(this.getApplicationContext()));
    }
}
