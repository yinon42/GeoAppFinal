package com.example.geoappfinal;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class StartMenuActivity extends AppCompatActivity {

    MaterialButton btnCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu); // Connects the XML layout to this activity

        // Initialize start button
        btnCountries = findViewById(R.id.startMenu_BTN);
        btnCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Countries Info Activity
                Intent intent = new Intent(StartMenuActivity.this, CountriesActivity.class);
                startActivity(intent);
            }
        });
    }
}