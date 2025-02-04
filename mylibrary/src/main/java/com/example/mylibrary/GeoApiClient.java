package com.example.mylibrary;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeoApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static GeoApiService apiService;

    public static GeoApiService getApiService() {
        if (apiService == null) {
            // יצירת OkHttpClient עם זמני המתנה מותאמים
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS) // זמן המתנה להתחברות
                    .readTimeout(60, TimeUnit.SECONDS)    // זמן המתנה לקריאת תגובה
                    .writeTimeout(60, TimeUnit.SECONDS)   // זמן המתנה לכתיבה
                    .build();

            // הגדרת Retrofit עם OkHttpClient המותאם
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // שימוש ב-OkHttpClient המותאם
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Log.d("GeoApiClient", "Server connected successfully");
            apiService = retrofit.create(GeoApiService.class);
        }
        return apiService;
    }
}








//package com.example.mylibrary;
//
//import android.util.Log;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class GeoApiClient {
//    private static final String BASE_URL = "http://10.0.2.2:8080";
//    private static GeoApiService apiService;
//
//    public static GeoApiService getApiService() {
//        if (apiService == null) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            Log.d("server_connected","connected");
//            apiService = retrofit.create(GeoApiService.class);
//        }
//        return apiService;
//    }
//}
