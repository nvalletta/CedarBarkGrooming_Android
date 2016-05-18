package com.cedarbarkgrooming.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nora on 5/16/2016.
 */
public class RestClient {

    public static final String OPEN_WEATHER_API_ROOT = "http://api.openweathermap.org/data/2.5/";
    public static final String GOOGLE_MAPS_API_ROOT = "https://maps.googleapis.com/maps/api/";

    private static OpenWeatherApi sOpenWeatherClient;
    private static GoogleApi sGoogleClient;

    static {
        initialize();
    }

    private RestClient() { }

    public static OpenWeatherApi getOpenWeatherRestClient() {
        return sOpenWeatherClient;
    }

    public static GoogleApi getGoogleRestClient() {
        return sGoogleClient;
    }

    private static void initialize() {
        Retrofit.Builder openWeatherApiBuilder = new Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_API_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        Retrofit.Builder googleApiBuilder = new Retrofit.Builder()
                .baseUrl(GOOGLE_MAPS_API_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        Retrofit openWeatherRestAdapter = openWeatherApiBuilder.build();
        Retrofit googleRestAdapter = googleApiBuilder.build();

        sOpenWeatherClient = openWeatherRestAdapter.create(OpenWeatherApi.class);
        sGoogleClient = googleRestAdapter.create(GoogleApi.class);
    }

}
