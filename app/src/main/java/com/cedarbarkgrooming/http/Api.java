package com.cedarbarkgrooming.http;

import com.cedarbarkgrooming.model.weather.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Nora on 5/16/2016.
 */
public interface Api {

    @GET("weather?APPID=" + WebUtils.OPENWEATHER_API_KEY)
    Observable<WeatherResponse> getWeatherDataForCity(@Query("q") String city);

}
