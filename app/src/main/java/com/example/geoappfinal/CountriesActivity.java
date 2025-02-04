package com.example.geoappfinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class CountriesActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteCountryName;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private EditText editTextLongitude;
    private EditText editTextLatitude;

    private Button buttonNext;
    private Button buttonBack;
    private Button buttonFindLocation;

    private LocationCallback locationCallback;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private GeoFunctionHelper funcHelper; // Instance of GeoFunctionHelper to call functions

    private static final String TAG = "CountriesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        initViews();
        initButtons();

        funcHelper = new GeoFunctionHelper(); // Initialize GeoFunctionHelper to use its functions
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        loadCountries();// Loads the list of countries to update the selection field
    }

    private void initButtons() {
        // מאזין ללחיצה על כפתור NEXT
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCountry = autoCompleteCountryName.getText().toString().trim();
                String longitudeStr = editTextLongitude.getText().toString().trim();
                String latitudeStr = editTextLatitude.getText().toString().trim();

                // בדיקת תקינות קלט
                if (selectedCountry.isEmpty()) {
                    Toast.makeText(CountriesActivity.this, "אנא בחר מדינה", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (longitudeStr.isEmpty() || latitudeStr.isEmpty()) {
                    Toast.makeText(CountriesActivity.this, "אנא הזן קו רוחב וקו אורך", Toast.LENGTH_SHORT).show();
                    return;
                }

                double lon, lat;
                try {
                    lon = Double.parseDouble(longitudeStr);
                    lat = Double.parseDouble(latitudeStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(CountriesActivity.this, "קואורדינטות לא חוקיות", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "Selected country: " + selectedCountry + ", Latitude: " + lat + ", Longitude: " + lon);
                // קריאה לפונקציה לזיהוי המדינה באמצעות AsyncTask
                new FindCountryTask().execute(lat, lon);
            }
        });

        // מאזין לכפתור "FIND BY YOUR LOCATION"
        buttonFindLocation.setOnClickListener(v -> {
            Log.d(TAG, "FIND BY YOUR LOCATION button clicked.");
            getCurrentLocationAndFindCountry();
        });

        // מאזין ללחיצה על כפתור BACK (חזרה למסך הקודם)
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // סוגר את הפעילות הנוכחית
            }
        });
    }

    private void initViews() {
        autoCompleteCountryName = findViewById(R.id.autoCompleteCountryName);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        buttonNext = findViewById(R.id.buttonNext);
        buttonBack = findViewById(R.id.buttonBack);
        buttonFindLocation = findViewById(R.id.buttonFindLocation);
    }

    private void getCurrentLocationAndFindCountry() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getCurrentLocationAndFindCountry: Requesting location permission.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        Log.d(TAG, "getCurrentLocationAndFindCountry: Requesting active location.");

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.e(TAG, "getCurrentLocationAndFindCountry: Location result is null.");
                    Toast.makeText(CountriesActivity.this, "לא ניתן לקבל מיקום. נסה שוב.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Location location = locationResult.getLastLocation();
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.d(TAG, "getCurrentLocationAndFindCountry: Current location - Latitude: " + latitude + ", Longitude: " + longitude);

                    // Stop location updates after receiving first location
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback);

                    // Continue to the next process
                    new FindCountryTask().execute(latitude, longitude);
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    // Loads the list of countries from the API and updates the AutoCompleteTextView.
    private void loadCountries() {
        // GeoFunctionHelper function to load the list of countries
        funcHelper.getAllCountries();

        autoCompleteCountryName.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> countries = GeoFunctionHelper.countryList;
                if (countries != null && !countries.isEmpty()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            CountriesActivity.this,
                            android.R.layout.simple_dropdown_item_1line,
                            countries
                    );
                    autoCompleteCountryName.setAdapter(adapter);
                    autoCompleteCountryName.showDropDown();
                    Log.d(TAG, "Countries dropdown updated: " + countries);
                } else {
                    Log.e(TAG, "Countries list is empty");
                }
            }
        }, 1000);
    }

    // AsyncTask to perform the findCountryByCoordinates function call asynchronously
    private class FindCountryTask extends AsyncTask<Double, Void, String> {
        private double lat, lon;

        @Override
        protected String doInBackground(Double... params) {
            lat = params[0];
            lon = params[1];
            Log.d(TAG, "Calling findCountryByCoordinates in background...");
            String result = funcHelper.findCountryByCoordinates(lat, lon);
            Log.d(TAG, "Country result: " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "onPostExecute: Country result: " + result);
            if (result == null || result.trim().isEmpty()) {
                Toast.makeText(CountriesActivity.this, "Error: Could not determine country", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent;
            String region = "Unknown"; // Default region

            if (result.contains("Israel")) {
                Log.d(TAG, "User is in Israel, determining region...");

                double netanyaLat = 32.3215; // Latitude of Netanya
                double rishonLat = 31.9718; // Latitude of Rishon LeZion

                Log.d(TAG, "User Coordinates: Latitude = " + lat + ", Longitude = " + lon);
                Log.d(TAG, "Determined region: " + region);

                if (lat > netanyaLat) {
                    region = "North"; // צפון
                } else if (lat <= netanyaLat && lat >= rishonLat) {
                    region = "Center"; // מרכז
                } else {
                    region = "South"; // דרום
                }

                Log.d(TAG, "Determined region: " + region);

                // Choosing the appropriate activity according to the region
                if (region.equals("North")) {
                    intent = new Intent(CountriesActivity.this, NorthIsraelRestaurantPage.class);
                } else if (region.equals("Center")) {
                    intent = new Intent(CountriesActivity.this, CenterIsraelRestaurantPage.class);
                } else {
                    intent = new Intent(CountriesActivity.this, SouthIsraelRestaurantPage.class);
                }

            } else if (result.contains("Lebanon")) {
                Log.d(TAG, "Navigating to LebanonRestaurantPage");
                intent = new Intent(CountriesActivity.this, LebanonRestaurantPage.class);
            } else {
                Toast.makeText(CountriesActivity.this, "Error: Country not supported", Toast.LENGTH_SHORT).show();
                return;
            }

            // Transferring data to the next screen
            Log.d(TAG, "Starting activity with data: Latitude = " + lat + ", Longitude = " + lon + ", Region = " + region);
            intent.putExtra("Latitude", lat);
            intent.putExtra("Longitude", lon);

            startActivity(intent);
        }






    }
}
