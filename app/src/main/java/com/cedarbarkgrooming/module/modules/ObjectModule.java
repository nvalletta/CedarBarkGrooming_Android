package com.cedarbarkgrooming.module.modules;

import android.location.Location;

import com.cedarbarkgrooming.model.reminders.Reminder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subjects.PublishSubject;

/**
 * Created by Nora on 5/14/2016.
 */
@Module
public class ObjectModule {

    @Provides
    @Singleton
    List<Reminder> providesReminders() {
        return new ArrayList<>();
    }

    @Provides
    @Singleton
    PublishSubject<Location> providesCurrentUserLocation() {
        return PublishSubject.create();
    }

}
