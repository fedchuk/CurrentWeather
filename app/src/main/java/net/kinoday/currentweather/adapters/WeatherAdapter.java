package net.kinoday.currentweather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.kinoday.currentweather.R;
import net.kinoday.currentweather.Weather;

import java.util.ArrayList;

/**
 * Created by Fedchuk Maxim on 13.01.2017.
 * Copyright (c) 2017 Paladin Engineering All rights reserved.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private ArrayList<Weather> objects;
    private Context mContext;

    public WeatherAdapter(Context context, ArrayList<Weather> weathers) {
        this.mContext = context;
        objects = weathers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Weather weather = objects.get(position);
        if (!weather.getIcon().equals("")) {
            Picasso.with(mContext)
                    .load(weather.getIcon())
                    .into(holder.imageWeather);
        }
        holder.textDate.setText(weather.getDate());
        holder.textTemp.setText(String.valueOf(weather.getTemp())+" ");
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setItems(ArrayList<Weather> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageWeather;
        private TextView textDate, textTemp;

        ViewHolder(View itemView) {
            super(itemView);
            textDate = (TextView) itemView.findViewById(R.id.text_date);
            textTemp = (TextView) itemView.findViewById(R.id.text_temperature);
            imageWeather = (ImageView) itemView.findViewById(R.id.image_icon_weather);
        }
    }
}
