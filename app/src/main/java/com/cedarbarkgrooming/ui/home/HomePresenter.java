package com.cedarbarkgrooming.ui.home;

import android.content.Intent;
import android.net.Uri;

import com.cedarbarkgrooming.CedarBarkGroomingApplication;

/**
 * Created by Nora on 5/7/2016.
 */
public class HomePresenter {

    private static final Uri CEDAR_BARK_URI = Uri.parse("https://www.google.com/maps/place/298+N+900+W,+Cedar+City,+UT+84721/@37.6825876,-113.0769967,17z/data=!3m1!4b1!4m5!3m4!1s0x80b56198c2942025:0xaec69677c68e45f0!8m2!3d37.6825876!4d-113.074808");

    public void onNavigateClick() {
        CedarBarkGroomingApplication applicationInstance = CedarBarkGroomingApplication.getApplication();
        if (null == applicationInstance) return;
        if (applicationInstance.isAppInstalled("com.google.android.apps.maps")) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, CEDAR_BARK_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            applicationInstance.startActivity(intent);
        }
    }

    public void onViewImageGalleryClick() {
        // todo: open image gallery
    }

    public void onManageRemindersClick() {
        // todo: open manage reminders
    }

}
