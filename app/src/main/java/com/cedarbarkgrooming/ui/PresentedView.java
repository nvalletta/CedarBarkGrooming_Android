package com.cedarbarkgrooming.ui;

import android.content.Context;

import com.cedarbarkgrooming.model.maps.Distance;
import com.cedarbarkgrooming.model.weather.Weather;

/**
 * Created by Nora on 5/15/2016.
 */
public interface PresentedView {
    Context getViewContext();

    void showError(String errorMessage);

    void dismiss();

    void displayDistanceInformation(Distance distance);

    void displayWeatherInformation(Weather weather);

    void hideDistanceText();

    void hideWeatherText();
}
