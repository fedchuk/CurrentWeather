package net.kinoday.currentweather.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fedchuk Maxim on 08.08.2017.
 * Copyright (c) 2017 Fedchuk Maxim. All rights reserved.
 */

public class GetWeekWeatherResponce {

    @SerializedName("hourly_forecast")
    @Expose
    private List<Hourly_forecast> hourlyForecast;

    public List<Hourly_forecast> getHourlyForecast() {
        return hourlyForecast;
    }

    public void setHourlyForecast(List<Hourly_forecast> hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }


    public class Hourly_forecast {
        @SerializedName("FCTTIME")
        @Expose
        FctTime fctTime;
        @SerializedName("temp")
        @Expose
        Temperature temperature;
        @SerializedName("icon_url")
        @Expose
        String iconLink;

        public FctTime getFctTime() {
            return fctTime;
        }

        public void setFctTime(FctTime fctTime) {
            this.fctTime = fctTime;
        }

        public Temperature getTemperature() {
            return temperature;
        }

        public void setTemperature(Temperature temperature) {
            this.temperature = temperature;
        }

        public String getIconLink() {
            return iconLink;
        }

        public void setIconLink(String iconLink) {
            this.iconLink = iconLink;
        }
    }

    public class FctTime {
        private int hour;
        private int year;
        private int mon;
        private int mday;

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMon() {
            return mon;
        }

        public void setMon(int mon) {
            this.mon = mon;
        }

        public int getMday() {
            return mday;
        }

        public void setMday(int mday) {
            this.mday = mday;
        }
    }

    public class Temperature {
        private int metric;

        public int getMetric() {
            return metric;
        }

        public void setMetric(int metric) {
            this.metric = metric;
        }
    }
}
