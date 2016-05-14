package com.cedarbarkgrooming.module;

import com.cedarbarkgrooming.ui.home.HomeActivity;
import com.cedarbarkgrooming.ui.reminders.RemindersActivity;

/**
 * Created by Nora on 5/7/2016.
 */
public interface Injector {
    void inject(HomeActivity target);
    void inject(RemindersActivity target);
}
