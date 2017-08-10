package net.kinoday.currentweather.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fedchuk Maxim on 08.08.2017.
 * Copyright (c) 2017 Fedchuk Maxim. All rights reserved.
 */

public class GetSearchCityResponce {

    @SerializedName("RESULTS")
    @Expose
    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public class Results {
        @SerializedName("name")
        @Expose
        String nameCity;
        @SerializedName("lat")
        @Expose
        String latitude;
        @SerializedName("lon")
        @Expose
        String longitude;

        public String getNameCity() {
            return nameCity;
        }

        public void setNameCity(String nameCity) {
            this.nameCity = nameCity;
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
}
