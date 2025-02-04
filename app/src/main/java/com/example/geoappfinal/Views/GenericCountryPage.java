package com.example.geoappfinal.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.geoappfinal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GenericCountryPage extends AppCompatActivity implements OnMapReadyCallback {

    private TextView textViewCountryName;
    private GoogleMap map;
    private double latitude;
    private double longitude;

    private Button buttonBack;

    private String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_country_page);


        // קבלת הנתונים מה-Intent
      //  countryName = getIntent().getStringExtra("CountryName");
        latitude = getIntent().getDoubleExtra("Latitude", 0.0);
        longitude = getIntent().getDoubleExtra("Longitude", 0.0);

        //textViewCountryName.setText("WELCOME TO\n" + countryName);


        // Initialize back button
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NorthIsraelRestaurantPage", "Back button clicked");
                finish();
            }
        });

        // אתחול המפה
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // הוספת נקודת ציון למיקום המדינה
        LatLng countryLocation = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(countryLocation).title("Selected Location"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLocation, 12));
    }
}
