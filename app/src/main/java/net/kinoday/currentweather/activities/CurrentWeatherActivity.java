package net.kinoday.currentweather.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import net.kinoday.currentweather.R;
import net.kinoday.currentweather.Weather;
import net.kinoday.currentweather.adapters.WeatherAdapter;
import net.kinoday.currentweather.model.WeatherModel;
import net.kinoday.currentweather.presenter.CurrentWeatherPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentWeatherActivity extends AppCompatActivity {

    private CurrentWeatherPresenter presenter;
    private LocationManager mLocationManager;
    private ImageView imageWeather;
    private TextView textCity, textTemperature;
    private ProgressBar progressBar;
    private WeatherAdapter weatherAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Weather> weatherWeek = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        init();
        //presenter.loadWeather();
    }

    private void init() {
        imageWeather = (ImageView) findViewById(R.id.image_icon_weather);
        textCity = (TextView) findViewById(R.id.text_name_city);
        textTemperature = (TextView) findViewById(R.id.text_number_temperature);
        progressBar = (ProgressBar) findViewById(R.id.progress_download_weather);
        recyclerView = (RecyclerView) findViewById(R.id.rvList);

        weatherAdapter = new WeatherAdapter(this, weatherWeek);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(weatherAdapter);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.app_url_response))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        LocationManager mLocationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = getLastKnownLocation();
        String strCoordinates = "49.9808100,36.2527200";
        if (location != null) {
            strCoordinates = location.getLatitude() + "," + location.getLongitude();
        } else {
            Toast.makeText(this, getString(R.string.failed_coordinites), Toast.LENGTH_SHORT).show();
        }

        WeatherModel weatherModel = new WeatherModel(retrofit, strCoordinates);
        presenter = new CurrentWeatherPresenter(weatherModel);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void showWeather(Weather weather, ArrayList<Weather> weatherWeek) {
        if (weatherWeek!=null) {
            this.weatherWeek = weatherWeek;
            weatherAdapter.setItems(weatherWeek);
        }
        if (weather!=null) {
            if (!weather.getIcon().equals("")) {
                Picasso.with(CurrentWeatherActivity.this)
                        .load(weather.getIcon())
                        .into(imageWeather);
            }
            textCity.setText(weather.getCity());
            textTemperature.setText(weather.getTemp()+" °C");
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
}
