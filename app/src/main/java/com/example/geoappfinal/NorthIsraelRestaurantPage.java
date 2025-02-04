package com.example.geoappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NorthIsraelRestaurantPage extends FragmentActivity implements OnMapReadyCallback {

    private Button buttonBack;

    private GoogleMap mMap;

    private double userLatitude = 31.7683, userLongitude = 35.2137; // Default: Jerusalem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.israel_north_resturant_page);
        Log.d("NorthIsraelRestaurantPage", "onCreate: Activity started");

        // Get the coordinates sent from the previous screen
        Intent intent = getIntent();
        if (intent != null) {
            userLatitude = intent.getDoubleExtra("Latitude", 31.7683);
            userLongitude = intent.getDoubleExtra("Longitude", 35.2137);
            Log.d("NorthIsraelRestaurantPage", "Received Latitude: " + userLatitude + ", Longitude: " + userLongitude);
        } else {
            Log.e("NorthIsraelRestaurantPage", "Intent is NULL!");
        }

        // Initialize back button
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NorthIsraelRestaurantPage", "Back button clicked");
                finish();
            }
        });

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // User location or default location
        LatLng userLocation = new LatLng(userLatitude, userLongitude);
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
    }
}
