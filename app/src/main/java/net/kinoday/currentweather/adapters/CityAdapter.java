package net.kinoday.currentweather.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.kinoday.currentweather.City;
import net.kinoday.currentweather.R;

import java.util.ArrayList;

/**
 * Created by Fedchuk Maxim on 13.01.2017.
 * Copyright (c) 2017 Paladin Engineering All rights reserved.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private static OnClinicClickListener mListener;
    private ArrayList<City> objects;
    private Context mContext;

    public CityAdapter(Context context, ArrayList<City> weathers) {
        this.mContext = context;
        objects = weathers;
    }

    public void setOnClinicClickListener(OnClinicClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final City city = objects.get(position);
        if (!city.getCity().equals("")) {
            holder.textCity.setText(city.getCity());
        }
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int)v.getTag();
                mListener.selectItem(objects.get(pos).getLatitude()+","+objects.get(pos).getLongitude(),
                        objects.get(pos).getCity());
            }
        });
    }

    public interface OnClinicClickListener {
        void selectItem(String coordinates, String city);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setItems(ArrayList<City> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textCity;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            textCity = (TextView) itemView.findViewById(R.id.text_city);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
