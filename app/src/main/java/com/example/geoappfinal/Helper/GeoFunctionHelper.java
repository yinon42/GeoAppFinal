package com.example.geoappfinal.Helper;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.CountryRequest;
import com.example.mylibrary.GeoApiClient;
import com.example.mylibrary.GeoApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

import retrofit2.Response;
import retrofit2.Call;


public class GeoFunctionHelper extends AppCompatActivity {

    public static List<String> countryList = new ArrayList<>(); // Static variable to store the list of countries

    // Function that finds the country where the point is located by coordinates
    public String findCountryByCoordinates(double latitude, double longitude) {
        try {
            GeoApiService apiService = GeoApiClient.getApiService();
            Map<String, Object> request = new HashMap<>();
            request.put("latitude", latitude);
            request.put("longitude", longitude);

            Call<ResponseBody> call = apiService.findCountryByCoordinates(request);

            Thread.sleep(2000); // 2 second pause to prevent overloads

            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                Log.e("GeoFunctionHelper", "Failed response: " + response.message());
                return "Failed to get response: " + response.message();
            }
        } catch (Exception e) {
            Log.e("GeoFunctionHelper", "Error while finding country by coordinates: " + e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
    }

    // Function that returns all the countries names in the DB
    public void getAllCountries() {
        GeoApiService apiService = GeoApiClient.getApiService();

        // קריאת ה-API עם Retrofit
        Call<List<String>> call = apiService.getAllCountries();

        // הרצת הקריאה ב-Thread נפרד
        new Thread(() -> {
            try {
                Log.d("Retrofit", "Fetching all countries...");
                Thread.sleep(2000); // השהייה בין קריאות API

                Response<List<String>> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {
                    countryList = response.body(); // שמירת רשימת המדינות
                    Log.d("Retrofit", "Countries received: " + response.body());
                } else {
                    Log.e("Retrofit", "Failed to fetch countries: " + response.message());
                    Log.e("Retrofit", "Error Code: " + response.code());

                    if (response.code() == 429) {
                        Log.e("Retrofit", "Too many requests (429). Try adding more delay.");
                    }
                }
            } catch (Exception e) {
                Log.e("Retrofit", "Error during API call: " + e.getMessage(), e);
            }
        }).start();
    }

    // Function that check if a point is in the chosen country (by coutry 2 letters code)
    public String checkCountry(String country, double latitude, double longitude) {
        try {
            Log.d("GeoFunctionHelper", "Checking country: " + country + " with coordinates: (" + latitude + ", " + longitude + ")");

            GeoApiService apiService = GeoApiClient.getApiService();
            CountryRequest request = new CountryRequest(country, latitude, longitude);
            Call<ResponseBody> call = apiService.checkCountry(request);

            Thread.sleep(2000); // 2 second pause to prevent overloads

            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                String result = response.body().string();
                Log.d("GeoFunctionHelper", "Response successful: " + result);
                return result;
            } else {
                Log.e("GeoFunctionHelper", "Failed response: " + response.message());
                Log.e("GeoFunctionHelper", "Error Code: " + response.code());

                if (response.code() == 429) {
                    Log.e("GeoFunctionHelper", "Server is blocking requests due to too many requests (429). Increase delay.");
                }

                return "Failed to get response: " + response.message();
            }
        } catch (Exception e) {
            Log.e("GeoFunctionHelper", "Error while checking country: " + e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
    }
}
