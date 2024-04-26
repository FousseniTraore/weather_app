package com.example.traore_fousseni_s2110850;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.traore_fousseni_s2110850.Adapters.CityWeatherAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AllCitiesWeatherInfoActivity extends AppCompatActivity {

    private ListView cityWeatherListView;
    private HashMap<String, String> cityUrls = new HashMap<>();
    private ArrayList<CityWeatherData> cityWeatherDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cities_weather_info);

        cityWeatherListView = findViewById(R.id.city_weather_list);

        // Populate cityUrls HashMap
        populateCityUrls();

        // Fetch data from URLs and populate ArrayList
        new FetchWeatherDataTask().execute();
    }

    private void populateCityUrls() {
        cityUrls.put("Glasgow", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2648579");
        cityUrls.put("London", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643743");
        cityUrls.put("New York", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/5128581");
        cityUrls.put("Oman", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/287286");
        cityUrls.put("Mauritius", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/934154");
        cityUrls.put("Bangladesh", "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/1185241");
    }

    private class FetchWeatherDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (String city : cityUrls.keySet()) {
                String url = cityUrls.get(city);
                Log.d("URLX",url);
                try {
                    URL feedUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) feedUrl.openConnection();
                    conn.setRequestMethod("GET");

                    InputStream inputStream = conn.getInputStream();

                    CityWeatherData weatherData = parseXml(inputStream);

                    cityWeatherDataList.add(weatherData);

                    conn.disconnect();
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            // Set adapter to ListView
            CityWeatherAdapter adapter = new CityWeatherAdapter(AllCitiesWeatherInfoActivity.this, cityWeatherDataList);
            cityWeatherListView.setAdapter(adapter);
        }
    }

    private CityWeatherData parseXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, null);

        String temperature = null;
        String time = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                if (tagName.equals("item")) {
                    while (eventType != XmlPullParser.END_TAG || !tagName.equals("item")) {
                        if (eventType == XmlPullParser.START_TAG) {
                            tagName = parser.getName();
                            if (tagName.equals("title")) {
                                String title = parser.nextText();
                                String[] parts = title.split(":");
                                if (parts.length >= 2) {
                                    time = parts[0].trim();
                                    String[] temperatureParts = parts[1].split(",");
                                    if (temperatureParts.length >= 2) {
                                        temperature = temperatureParts[1].trim();
                                    }
                                }
                            }
                        }
                        eventType = parser.next();
                        tagName = parser.getName();
                    }
                }
            }
            eventType = parser.next();
        }
        return new CityWeatherData(time, temperature);
    }
}
