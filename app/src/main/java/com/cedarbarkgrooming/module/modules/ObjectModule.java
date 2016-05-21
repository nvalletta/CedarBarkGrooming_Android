package com.cedarbarkgrooming.module.modules;

import com.cedarbarkgrooming.R;
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
    BehaviorSubject<String> providesDistanceSubject() {
        return BehaviorSubject.create();
    }

    @Provides
    @Singleton
    BehaviorSubject<CedarBarkGroomingWeather> providesWeatherSubject() {
        return BehaviorSubject.create();
    }

    @Provides
    @Singleton
    Integer [] providesBeforeAndAfterThumbnailResourceIds() {
        return new Integer[]{
                R.drawable.aww_before, R.drawable.aww_after,
                R.drawable.fuzzyschnau_before, R.drawable.fuzzyschnau_after,
                R.drawable.hairy_before, R.drawable.hairy_after,
                R.drawable.happydog_before, R.drawable.happydog_after,
                R.drawable.maltese_before, R.drawable.maltese_after,
                R.drawable.mix_before, R.drawable.mix_after,
                R.drawable.poodly_before, R.drawable.poodly_after,
                R.drawable.schnauser_before, R.drawable.schnauser_after,
                R.drawable.scruffypup_before, R.drawable.scruffypup_after,
                R.drawable.shaggy_before, R.drawable.shaggy_after,
                R.drawable.shihtzu_before, R.drawable.shihtzu_after
        };
    }

}
