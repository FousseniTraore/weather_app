package com.example.traore_fousseni_s2110850;

public class CityWeatherData {
    private String cityName;
    private String temperature;
    private String time;

    public CityWeatherData(String temperature, String time) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.time = time;
    }

    public String getCityName() {
        return cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTime() {
        return time;
    }
}
