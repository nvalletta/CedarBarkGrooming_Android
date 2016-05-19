package com.cedarbarkgrooming.model.weather;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by Nora on 5/16/2016.
 */
public class CedarBarkGroomingWeather {

    String weatherDescription;
    double currentTemperature;
    Date dateOfWeatherUpdate;

    public CedarBarkGroomingWeather(String weatherDescription, double currentTemperature) {
        this.weatherDescription = weatherDescription;
        this.currentTemperature = currentTemperature;
        dateOfWeatherUpdate = new Date();
    }

    public double getCurrentTemperatureFahrenheit() {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format((currentTemperature - 273.15) * 1.8 + 32));
    }

    /**
     * Checks dateOfWeatherUpdate to see if enough time has passed since the last weather update.
     * @param hours number of hours that should have elapsed since the last update
     * @return whether or not "hours" many hours have passed since we last updated this weather object.
     */
    public boolean enoughTimeHasPassedSinceLastUpdate(int hours) {
        Date now = new Date();
        if (now.after(dateOfWeatherUpdate)) {
            long timeInMilliseconds = 1000 * 60 * 60 * hours;
            long elapsedTime = now.getTime() - dateOfWeatherUpdate.getTime();
            Log.w("CedarBarkWeather", "timeInMilliseconds: " + timeInMilliseconds + " , " + "elapsedTime: " + elapsedTime);
            if (timeInMilliseconds < elapsedTime) {
                return true;
            }
        }
        return false;
    }

}
