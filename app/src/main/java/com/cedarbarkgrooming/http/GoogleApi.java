package com.cedarbarkgrooming.http;

import com.cedarbarkgrooming.model.maps.MapsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GoogleApi {

    @GET("directions/json?destination=Cedar City,UT&key=" + WebUtils.GOOGLE_API_KEY)
    Observable<MapsResponse> getDistanceDataForLocation(@Query("origin") String origin);

}
