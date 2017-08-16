package net.kinoday.currentweather.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import net.kinoday.currentweather.City;
import net.kinoday.currentweather.R;
import net.kinoday.currentweather.Weather;
import net.kinoday.currentweather.adapters.CityAdapter;
import net.kinoday.currentweather.adapters.WeatherAdapter;
import net.kinoday.currentweather.model.WeatherModel;
import net.kinoday.currentweather.presenter.SearchCityPresenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchWeatherActivity extends AppCompatActivity implements CityAdapter.OnClinicClickListener {

    private SearchCityPresenter presenter;
    private ImageView imageWeather;
    private TextView textCity, textTemperature;
    private EditText editSearch;
    private ProgressBar progressBar;
    private CityAdapter cityAdapter;
    private WeatherAdapter weatherAdapter;
    private RecyclerView recyclerViewWeather, recyclerViewCity;
    private ArrayList<City> cities = new ArrayList<>(); // список городов
    private ArrayList<Weather> weatherWeek = new ArrayList<>(); // погода на неделю

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        init();
        //presenter.loadWeather("");
    }

    private void init() {
        imageWeather = (ImageView) findViewById(R.id.image_icon_weather);
        textCity = (TextView) findViewById(R.id.text_name_city);
        textTemperature = (TextView) findViewById(R.id.text_number_temperature);
        progressBar = (ProgressBar) findViewById(R.id.progress_download_weather);
        recyclerViewWeather = (RecyclerView) findViewById(R.id.rvList);
        recyclerViewCity = (RecyclerView) findViewById(R.id.rvListCity);
        editSearch = (EditText) findViewById(R.id.edit_search);

        cityAdapter = new CityAdapter(this, cities);
        weatherAdapter = new WeatherAdapter(this, weatherWeek);
        LinearLayoutManager llmCity = new LinearLayoutManager(this);
        recyclerViewCity.setLayoutManager(llmCity);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerViewCity.setItemAnimator(itemAnimator);
        recyclerViewCity.setAdapter(cityAdapter);
        cityAdapter.setOnClinicClickListener(this);
        LinearLayoutManager llmWeather = new LinearLayoutManager(this);
        recyclerViewWeather.setLayoutManager(llmWeather);
        recyclerViewWeather.setItemAnimator(itemAnimator);
        recyclerViewWeather.setAdapter(weatherAdapter);

        //presenter.viewIsReady();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>2) {
                    WeatherModel weatherModel = new WeatherModel(getRequests(getResources().getString(R.string.app_url_response_auto)), "");
                    presenter = new SearchCityPresenter(weatherModel);
                    presenter.attachView(SearchWeatherActivity.this);

                    hideWeather();
                    presenter.getSearch(s.toString());
                } else {
                    hideProgress();
                }
            }
        });
    }

    public void showCity(ArrayList<City> cities) {
        if (cities!=null && cities.size()>0) {
            this.cities = cities;
            cityAdapter.setItems(cities);
            showCity();
        } else {
            hideCity();
        }
    }

    public void showWeather(ArrayList<Weather> weatherWeek) {
        if (weatherWeek!=null) {
            hideCity();
            showWeather();
            this.weatherWeek = weatherWeek;
            weatherAdapter.setItems(weatherWeek);
            if (weatherWeek.size()>0) {
                if (!weatherWeek.get(0).getIcon().equals("")) {
                    Picasso.with(SearchWeatherActivity.this)
                            .load(weatherWeek.get(0).getIcon())
                            .into(imageWeather);
                }
                textTemperature.setText(weatherWeek.get(0).getTemp()+" °C");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void selectItem(String coordinates, String city) {
        hideProgress();
        hideCity();
        textCity.setText(city);

        WeatherModel weatherModel = new WeatherModel(getRequests(getResources().getString(R.string.app_url_response)), "");
        presenter = new SearchCityPresenter(weatherModel);
        presenter.attachView(this);

        presenter.getWeather(coordinates);
    }

    private void hideWeather() {
        recyclerViewWeather.setVisibility(View.GONE);
        imageWeather.setVisibility(View.GONE);
        textCity.setVisibility(View.GONE);
        textTemperature.setVisibility(View.GONE);
    }

    private void showWeather() {
        recyclerViewWeather.setVisibility(View.VISIBLE);
        imageWeather.setVisibility(View.VISIBLE);
        textCity.setVisibility(View.VISIBLE);
        textTemperature.setVisibility(View.VISIBLE);
    }

    private Retrofit getRequests(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    private void hideCity() {
        recyclerViewCity.setVisibility(View.GONE);
    }

    private void showCity() {
        recyclerViewCity.setVisibility(View.VISIBLE);
    }
}
