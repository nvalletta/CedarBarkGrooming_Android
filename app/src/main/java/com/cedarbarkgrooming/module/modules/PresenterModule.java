package com.cedarbarkgrooming.module.modules;

import com.cedarbarkgrooming.ui.home.HomePresenter;
import com.cedarbarkgrooming.ui.reminders.RemindersPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nora on 5/7/2016.
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    HomePresenter providesHomePresenter() {
        return new HomePresenter();
    }

    @Provides
    @Singleton
    RemindersPresenter providesRemindersPresenter() {
        return new RemindersPresenter();
    }

}
