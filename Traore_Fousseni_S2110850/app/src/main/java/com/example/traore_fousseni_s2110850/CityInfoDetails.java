package com.example.traore_fousseni_s2110850;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CityInfoDetails extends AppCompatActivity {

    private TextView temperatureTextView;
    private TextView windSpeedTextView;
    private TextView humidityTextView;
    private TextView pressureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info_details);

        // Initialize TextViews
        temperatureTextView = findViewById(R.id.temperature);
        windSpeedTextView = findViewById(R.id.windspeed);
        humidityTextView = findViewById(R.id.humidity);
        pressureTextView = findViewById(R.id.pressure);

        // RSS feed URL
        String feedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643123";

        // Parse RSS feed and update TextViews in a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                String temperature = "";
                String windSpeed = "";
                String humidity = "";
                String pressure = "";

                try {
                    parseRssFeed(feedUrl, temperature, windSpeed, humidity, pressure);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            temperatureTextView.setText("Temperature: " + temperature);
                            windSpeedTextView.setText("Wind Speed: " + windSpeed);
                            humidityTextView.setText("Humidity: " + humidity);
                            pressureTextView.setText("Pressure: " + pressure);
                        }
                    });
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseRssFeed(String feedUrl, String temperature, String windSpeed, String humidity, String pressure) throws XmlPullParserException, IOException {
        // Create a new URL object
        URL url = new URL(feedUrl);

        // Open a connection to the RSS feed
        URLConnection connection = url.openConnection();

        // Create a new PullParser instance
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        // Set the input stream for the parser
        parser.setInput(connection.getInputStream(), null);

        // Start parsing the XML
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    // Handle start tags here
                    if (tagName.equals("description")) {
                        // Extract the description
                        String description = parser.nextText();
                        // Parse the description to extract the required information
                        parseDescription(description, temperature, windSpeed, humidity, pressure);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    // Handle end tags if needed
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseDescription(String description, String temperature, String windSpeed, String humidity, String pressure) {
        // Split the description string by comma (,)
        String[] parts = description.split(",");

        // Extract temperature from the first part
        temperature = parts[0].split(":")[1].trim();

        // Extract wind speed from the appropriate part
        for (String part : parts) {
            if (part.contains("Wind Speed")) {
                windSpeed = part.split(":")[1].trim();
                break;
            }
        }

        // Extract humidity from the appropriate part
        for (String part : parts) {
            if (part.contains("Humidity")) {
                humidity = part.split(":")[1].trim();
                break;
            }
        }

        // Extract pressure from the appropriate part
        for (String part : parts) {
            if (part.contains("Pressure")) {
                pressure = part.split(":")[1].trim();
                break;
            }
        }
    }
}