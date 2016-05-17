package com.cedarbarkgrooming.http;

import com.cedarbarkgrooming.model.weather.WeatherResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Nora on 5/16/2016.
 */
public interface Api {

    @GET("weather?q={cityName},{countryCode}&APPID=" + WebUtils.OPENWEATHER_API_KEY)
    Observable<WeatherResponse> getWeatherDataForCity(String cityName, String countryCode);

}
