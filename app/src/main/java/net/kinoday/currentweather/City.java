package net.kinoday.currentweather;

/**
 * Created by Fedchuk Maxim on 08.08.2017.
 * Copyright (c) 2017 Fedchuk Maxim. All rights reserved.
 */

public class City {

    String city;
    String latitude;
    String longitude;

    public City(String city, String latitude, String longitude) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
