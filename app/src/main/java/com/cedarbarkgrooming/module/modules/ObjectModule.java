package com.cedarbarkgrooming.module.modules;

import com.cedarbarkgrooming.model.reminders.Reminder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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

}
