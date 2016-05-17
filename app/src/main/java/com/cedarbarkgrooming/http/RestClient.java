package com.cedarbarkgrooming.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Nora on 5/16/2016.
 */
public class RestClient {

    private static final String ROOT = "http://api.openweathermap.org/data/2.5/";
    private static Api sRestClient;

    static {
        initialize();
    }

    private RestClient() { }

    public static Api getRestClient() {
        return sRestClient;
    }

    private static void initialize() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ROOT)
                .client(new OkHttpClient());

        Retrofit restAdapter = builder.build();
        sRestClient = restAdapter.create(Api.class);
    }

}
