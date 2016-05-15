package com.cedarbarkgrooming.ui.home;

import com.cedarbarkgrooming.model.Reminder;
import com.cedarbarkgrooming.ui.Presenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Nora on 5/7/2016.
 */
public class HomePresenter extends Presenter {

    @Inject
    List<Reminder> mReminders;

    public void onPresentedViewCreated() {
        //todo: get user's location and calculate distance. get weather.
    }

    public void onViewImageGalleryClick() {
        // todo: open image gallery
    }

}
