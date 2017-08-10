package net.kinoday.currentweather.presenter;


import net.kinoday.currentweather.Weather;
import net.kinoday.currentweather.activities.CurrentWeatherActivity;
import net.kinoday.currentweather.model.WeatherModel;

import java.util.ArrayList;

/**
 * Created by Fedchuk Maxim on 08.08.2017.
 * Copyright (c) 2017 Fedchuk Maxim. All rights reserved.
 */

public class CurrentWeatherPresenter {

    private WeatherModel model;
    private CurrentWeatherActivity view;

    public CurrentWeatherPresenter(WeatherModel model) {
        this.model = model;
    }

    public void attachView(CurrentWeatherActivity currentWeatherActivity) {
        view = currentWeatherActivity;
    }

    public void viewIsReady() {
        loadWeather();
    }

    public void detachView() {
        view = null;
    }

    public void loadWeather() {
        view.showProgress();
        model.loadWeather(new WeatherModel.LoadWeatherCallback() {
            @Override
            public void onLoad(Weather weather, ArrayList<Weather> weatherWeek) {
                view.showWeather(weather, weatherWeek);
                view.hideProgress();
            }
        });
    }
}
