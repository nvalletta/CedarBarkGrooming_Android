package com.cedarbarkgrooming.model.weather;

import java.text.DecimalFormat;

/**
 * Created by Nora on 5/16/2016.
 */
public class CedarBarkGroomingWeather {

    String weatherDescription;
    double currentTemperature;

    public CedarBarkGroomingWeather(String weatherDescription, double currentTemperature) {
        this.weatherDescription = weatherDescription;
        this.currentTemperature = currentTemperature;
    }

    public double getCurrentTemperatureFahrenheit() {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format((currentTemperature - 273.15) * 1.8 + 32));
    }

}
