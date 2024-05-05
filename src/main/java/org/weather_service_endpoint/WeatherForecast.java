package org.weather_service_endpoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherForecast {
    private double latitude;
    private double longitude;
    private List<Date> date;
    private List<Integer> weatherCode;
    private List<Double> minTemperature;
    private List<Double> maxTemperature;
    private List<Double> sunshineDuration;

    public WeatherForecast(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setDate(List<Date> date) {
        this.date = date;
    }

    public void setWeatherCode(List<Integer> weatherCode) {
        this.weatherCode = weatherCode;
    }

    public void setMinTemperature(List<Double> minTemperature) {
        this.minTemperature = minTemperature;
    }

    public void setMaxTemperature(List<Double> maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setSunshineDuration(List<Double> sunshineDuration) {
        this.sunshineDuration = sunshineDuration;
    }

    public List<DailyForecast> getDailyForecasts() {
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            dailyForecasts.add(
                    new DailyForecast(
                            date.get(i),
                            weatherCode.get(i),
                            minTemperature.get(i),
                            maxTemperature.get(i),
                            sunshineDuration.get(i)
                    )
            );
        }
        return dailyForecasts;
    }
}
