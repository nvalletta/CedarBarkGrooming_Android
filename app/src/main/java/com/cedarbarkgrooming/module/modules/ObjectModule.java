package com.cedarbarkgrooming.module.modules;

import com.cedarbarkgrooming.model.reminders.Reminder;
import com.cedarbarkgrooming.model.weather.CedarBarkGroomingWeather;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subjects.BehaviorSubject;

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
    BehaviorSubject<String> getDistanceSubject() {
        return BehaviorSubject.create();
    }

    @Provides
    @Singleton
    BehaviorSubject<CedarBarkGroomingWeather> getWeatherSubject() {
        return BehaviorSubject.create();
    }

}
