package com.cedarbarkgrooming.ui.home;

import com.cedarbarkgrooming.model.maps.Distance;
import com.cedarbarkgrooming.model.weather.CedarBarkGroomingWeather;
import com.cedarbarkgrooming.ui.PresentedView;

public interface HomeView extends PresentedView {
    void displayDistanceInformation(Distance distance);

    void displayWeatherInformation(CedarBarkGroomingWeather weather);

    void hideDistanceText();

    void hideWeatherText();
}
