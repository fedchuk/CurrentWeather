package net.kinoday.currentweather.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fedchuk Maxim on 08.08.2017.
 * Copyright (c) 2017 Fedchuk Maxim. All rights reserved.
 */

public class GetTodayWeatherResponce {

    @SerializedName("current_observation")
    @Expose
    private CurrentObservation currentObservation;

    public CurrentObservation getCurrentObservation() {
        return currentObservation;
    }

    public void setCurrentObservation(CurrentObservation currentObservation) {
        this.currentObservation = currentObservation;
    }

    public class CurrentObservation {
        @SerializedName("temp_c")
        @Expose
        double temperature;
        @SerializedName("icon_url")
        @Expose
        String iconLink;
        @SerializedName("observation_location")
        @Expose
        ObservationLocation observationLocation;

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String getIconLink() {
            return iconLink;
        }

        public void setIconLink(String iconLink) {
            this.iconLink = iconLink;
        }

        public ObservationLocation getDisplayLocation() {
            return observationLocation;
        }

        public void setDisplayLocation(ObservationLocation observationLocation) {
            this.observationLocation = observationLocation;
        }
    }

    public class ObservationLocation {
        @SerializedName("full")
        @Expose
        String cityName;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
}
