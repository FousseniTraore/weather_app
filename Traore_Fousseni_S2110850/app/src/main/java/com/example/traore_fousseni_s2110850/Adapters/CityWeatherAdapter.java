package com.example.traore_fousseni_s2110850.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.traore_fousseni_s2110850.CityWeatherData;
import com.example.traore_fousseni_s2110850.R;

import java.util.ArrayList;

public class CityWeatherAdapter extends ArrayAdapter<CityWeatherData> {
    private ArrayList<CityWeatherData> cityWeatherDataList;
    private LayoutInflater inflater;

    public CityWeatherAdapter(Context context, ArrayList<CityWeatherData> cityWeatherDataList) {
        super(context, 0, cityWeatherDataList);
        this.cityWeatherDataList = cityWeatherDataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cities_item_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cityNameTextView = convertView.findViewById(R.id.city_name);
            viewHolder.temperatureTextView = convertView.findViewById(R.id.temperature);
            viewHolder.timeTextView = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CityWeatherData cityWeatherData = getItem(position);

        viewHolder.cityNameTextView.setText(cityWeatherData.getCityName());
        viewHolder.temperatureTextView.setText(cityWeatherData.getTemperature());
        viewHolder.timeTextView.setText(cityWeatherData.getTime());

        return convertView;
    }

    static class ViewHolder {
        TextView cityNameTextView;
        TextView temperatureTextView;
        TextView timeTextView;
    }
}
