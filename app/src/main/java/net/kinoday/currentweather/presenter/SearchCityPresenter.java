package net.kinoday.currentweather.presenter;


import net.kinoday.currentweather.City;
import net.kinoday.currentweather.Weather;
import net.kinoday.currentweather.activities.SearchWeatherActivity;
import net.kinoday.currentweather.model.WeatherModel;

import java.util.ArrayList;

/**
 * Created by Fedchuk Maxim on 08.08.2017.
 * Copyright (c) 2017 Fedchuk Maxim. All rights reserved.
 */

public class SearchCityPresenter {

    private WeatherModel model;
    private SearchWeatherActivity view;

    public SearchCityPresenter(WeatherModel model) {
        this.model = model;
    }

    public void attachView(SearchWeatherActivity searchWeatherActivity) {
        view = searchWeatherActivity;
    }

    public void viewIsReady() {
        //loadWeather("");
    }

    public void getSearch(String search) {
        loadCity(search);
    }

    public void getWeather(String coordinates) {
        loadWeather(coordinates);
    }

    public void detachView() {
        view = null;
    }

    public void loadCity(String search) {
        view.showProgress();
        model.searchWeather(new WeatherModel.SearchCityCallback() {
            @Override
            public void onLoad(ArrayList<City> cities) {
                view.showCity(cities);
                view.hideProgress();
            }
        }, search);
    }

    public void loadWeather(String coordinates) {
        view.showProgress();
        model.loadWeekWeatherSearch(new WeatherModel.LoadWeatherSearchCallback() {
            @Override
            public void onLoad(ArrayList<Weather> weatherWeek) {

                view.showWeather(weatherWeek);
                view.hideProgress();
            }
        }, coordinates);
    }
}
