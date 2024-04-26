/*  Starter project for Mobile Platform Development in main diet 2023/2024
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Fousseni Traore
// Student ID           _________________
// Programme of Study   Computing
//

// UPDATE THE PACKAGE NAME to include your Student Identifier


package com.example.traore_fousseni_s2110850;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        Button details = findViewById(R.id.details);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Transition to WeatherActivity
                startActivity(new Intent(MainActivity.this, AllCitiesWeatherInfoActivity.class));
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CityInfoDetails.class));
            }
        });
    }
}
