package com.cedarbarkgrooming.module;

import com.cedarbarkgrooming.sync.CedarBarkSyncAdapter;
import com.cedarbarkgrooming.ui.gallery.GalleryActivity;
import com.cedarbarkgrooming.ui.gallery.GridBaseAdapter;
import com.cedarbarkgrooming.ui.gallery.SingleImageFragment;
import com.cedarbarkgrooming.ui.home.HomeActivity;
import com.cedarbarkgrooming.ui.home.HomePresenter;
import com.cedarbarkgrooming.ui.reminders.CreateReminderActivity;
import com.cedarbarkgrooming.ui.reminders.CreateReminderPresenter;
import com.cedarbarkgrooming.ui.reminders.ReminderAdapter;
import com.cedarbarkgrooming.ui.reminders.ReminderItem;
import com.cedarbarkgrooming.ui.reminders.RemindersActivity;
import com.cedarbarkgrooming.ui.reminders.RemindersPresenter;

/**
 * Created by Nora on 5/7/2016.
 */
public interface Injector {

    void inject(HomeActivity target);

    void inject(RemindersActivity target);

    void inject(CreateReminderActivity target);

    void inject(ReminderItem target);

    void inject(CreateReminderPresenter target);

    void inject(ReminderAdapter target);

    void inject(RemindersPresenter target);

    void inject(HomePresenter target);

    void inject(CedarBarkSyncAdapter target);

    void inject(GalleryActivity target);

    void inject(GridBaseAdapter target);

    void inject(SingleImageFragment target);
}
