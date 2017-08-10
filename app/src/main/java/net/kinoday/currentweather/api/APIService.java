package net.kinoday.currentweather.api;

import net.kinoday.currentweather.responce.GetSearchCityResponce;
import net.kinoday.currentweather.responce.GetTodayWeatherResponce;
import net.kinoday.currentweather.responce.GetWeekWeatherResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Fedchuk Maxim on 27.12.2016.
 * Copyright (c) 2017 Paladin Engineering All rights reserved.
 */

public interface APIService {

    // получить текущую погоду
    @GET("api/e81e624bbdf81216/conditions/q/{coordinates}.json")
    Call<GetTodayWeatherResponce> getCurrentWeather(@Path("coordinates") String coordinates);
    // получить недельную погоду
    @GET("api/e81e624bbdf81216/hourly10day/q/{coordinates}.json")
    Call<GetWeekWeatherResponce> getWeekWeather(@Path("coordinates") String coordinates);
    // поиск города
    @GET("aq")
    Call<GetSearchCityResponce> getSearchCity(@Query("query") String query);
}
