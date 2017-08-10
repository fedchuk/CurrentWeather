package net.kinoday.currentweather.model;

import android.util.Log;

import net.kinoday.currentweather.City;
import net.kinoday.currentweather.Weather;
import net.kinoday.currentweather.api.APIService;
import net.kinoday.currentweather.responce.GetSearchCityResponce;
import net.kinoday.currentweather.responce.GetTodayWeatherResponce;
import net.kinoday.currentweather.responce.GetWeekWeatherResponce;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Fedchuk Maxim on 08.08.2017.
 * Copyright (c) 2017 Fedchuk Maxim. All rights reserved.
 */

public class WeatherModel {

    private Retrofit retrofit;
    private String coordinates;

    public WeatherModel(Retrofit retrofit, String coordinates) {
        this.retrofit = retrofit;
        this.coordinates = coordinates;
    }

    // загрузка погоды
    public void loadWeather(final LoadWeatherCallback callback) {
        APIService apiService = retrofit.create(APIService.class);
        Call<GetTodayWeatherResponce> call = apiService.getCurrentWeather(coordinates);
        call.enqueue(new Callback<GetTodayWeatherResponce>() {
            @Override
            public void onResponse(Call<GetTodayWeatherResponce> call, Response<GetTodayWeatherResponce> response) {
                if (response.body() != null) {
                    Weather weather = new Weather(response.body().getCurrentObservation().getDisplayLocation().getCityName(),
                            response.body().getCurrentObservation().getTemperature(),
                            response.body().getCurrentObservation().getIconLink(), "");
                    loadWeekWeather(callback, weather);
                }
            }

            @Override
            public void onFailure(Call<GetTodayWeatherResponce> call, Throwable t) {
                Log.d("TEST", "!!!" + t.toString());
            }
        });
    }

    // загрузка погоды за неделю
    public void loadWeekWeather(final LoadWeatherCallback callback, final Weather weather) {
        final ArrayList<Weather> weatherWeek = new ArrayList<>();
        final APIService apiService = retrofit.create(APIService.class);
        final Call<GetWeekWeatherResponce> call = apiService.getWeekWeather(coordinates);
        call.enqueue(new Callback<GetWeekWeatherResponce>() {
            @Override
            public void onResponse(Call<GetWeekWeatherResponce> call, Response<GetWeekWeatherResponce> response) {
                if (response.body() != null) {
                    if (response.body().getHourlyForecast().size()>0) {
                        int tmpTempMax = 0;
                        String tmpDate = "", tmpLink="";
                        for (int i=0; i<response.body().getHourlyForecast().size(); i++) {
                            GetWeekWeatherResponce.Hourly_forecast hf = response.body().getHourlyForecast().get(i);
                            if (hf!=null) {
                                String tmpDt = hf.getFctTime().getMday() + "-" + hf.getFctTime().getMon()
                                        + "-" + hf.getFctTime().getYear();
                                if (hf.getFctTime().getHour()==12) {
                                    tmpLink = hf.getIconLink();
                                }
                                if (tmpDate.equals("")) {
                                    tmpTempMax = hf.getTemperature().getMetric();
                                    tmpDate = tmpDt;
                                } else {
                                    if (tmpDate.equals(tmpDt) || i==response.body().getHourlyForecast().size()-1) {
                                        if (hf.getTemperature().getMetric()>tmpTempMax) {
                                            tmpTempMax = hf.getTemperature().getMetric();
                                        }
                                    } else {
                                        weatherWeek.add(new Weather("",tmpTempMax,tmpLink,tmpDate));
                                        tmpTempMax = 0;
                                        tmpDate = tmpDt;
                                    }
                                }
                                if (i==0) {
                                    weather.setTemp(hf.getTemperature().getMetric());
                                    weather.setIcon(hf.getIconLink());
                                }
                            }
                        }
                    }
                    callback.onLoad(weather, weatherWeek);
                }
            }

            @Override
            public void onFailure(Call<GetWeekWeatherResponce> call, Throwable t) {
                Log.d("TEST", "!!!" + t.toString());
            }
        });
    }

    // поиск города
    public void searchWeather(final SearchCityCallback callback, String search) {
        Log.d("TEST", "s="+search);
        final ArrayList<City> cities = new ArrayList<>();
        APIService apiService = retrofit.create(APIService.class);
        Call<GetSearchCityResponce> call = apiService.getSearchCity(search);
        call.enqueue(new Callback<GetSearchCityResponce>() {
            @Override
            public void onResponse(Call<GetSearchCityResponce> call, Response<GetSearchCityResponce> response) {
                if (response.body() != null) {
                    if (response.body().getResults().size()>0) {
                        for (int i=0; i<response.body().getResults().size(); i++) {
                            GetSearchCityResponce.Results results = response.body().getResults().get(i);
                            cities.add(new City(results.getNameCity(), results.getLatitude(), results.getLongitude()));
                        }
                        callback.onLoad(cities);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSearchCityResponce> call, Throwable t) {
                Log.d("TEST", "!!!" + t.toString());
            }
        });
    }

    // загрузка погоды за неделю для поиска
    public void loadWeekWeatherSearch(final LoadWeatherSearchCallback callback, String coordinates) {
        final ArrayList<Weather> weatherWeek = new ArrayList<>();
        final APIService apiService = retrofit.create(APIService.class);
        final Call<GetWeekWeatherResponce> call = apiService.getWeekWeather(coordinates);
        call.enqueue(new Callback<GetWeekWeatherResponce>() {
            @Override
            public void onResponse(Call<GetWeekWeatherResponce> call, Response<GetWeekWeatherResponce> response) {
                if (response.body() != null) {
                    if (response.body().getHourlyForecast().size()>0) {
                        int tmpTempMax = 0;
                        String tmpDate = "", tmpLink="";
                        for (int i=0; i<response.body().getHourlyForecast().size(); i++) {
                            GetWeekWeatherResponce.Hourly_forecast hf = response.body().getHourlyForecast().get(i);
                            if (hf!=null) {
                                String tmpDt = hf.getFctTime().getMday() + "-" + hf.getFctTime().getMon()
                                        + "-" + hf.getFctTime().getYear();
                                if (hf.getFctTime().getHour()==12) {
                                    tmpLink = hf.getIconLink();
                                }
                                if (tmpDate.equals("")) {
                                    tmpTempMax = hf.getTemperature().getMetric();
                                    tmpDate = tmpDt;
                                } else {
                                    if (tmpDate.equals(tmpDt) || i==response.body().getHourlyForecast().size()-1) {
                                        if (hf.getTemperature().getMetric()>tmpTempMax) {
                                            tmpTempMax = hf.getTemperature().getMetric();
                                        }
                                    } else {
                                        weatherWeek.add(new Weather("",tmpTempMax,tmpLink,tmpDate));
                                        tmpTempMax = 0;
                                        tmpDate = tmpDt;
                                    }
                                }
                            }
                        }
                    }
                    callback.onLoad(weatherWeek);
                }
            }

            @Override
            public void onFailure(Call<GetWeekWeatherResponce> call, Throwable t) {
                Log.d("TEST", "!!!" + t.toString());
            }
        });
    }

    public interface LoadWeatherCallback {
        void onLoad(Weather weather, ArrayList<Weather> weatherWeek);
    }

    public interface SearchCityCallback {
        void onLoad(ArrayList<City> cities);
    }

    public interface LoadWeatherSearchCallback {
        void onLoad(ArrayList<Weather> weatherWeek);
    }
}
