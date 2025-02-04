package com.example.mylibrary;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GeoApiService {

    @POST("/api/check-country")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> checkCountry(@Body CountryRequest request);

    @GET("/api/countries")
    Call<List<String>> getAllCountries();


    // הוספת פונקציה לקריאה לפונקציה findCountryByCoordinates בשרת
    @POST("/api/find-country")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> findCountryByCoordinates(@Body Map<String, Object> request);


    // הוספת הפונקציה לקריאה לפונקציה מהשרת
    @GET("/country/{countryCode}")
    Call<Map<String, Object>> getCountryData(@Path("countryCode") String countryCode);

}

