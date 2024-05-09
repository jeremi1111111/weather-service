package org.weather_service_endpoint;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class DailyForecast {
    private final static double PV_POWER = 2.5;
    private final static double PV_EFFICIENCY = 0.2;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;
    private int weatherCode;
    private double minTemperature;
    private double maxTemperature;
    private double pvProduction;

    public DailyForecast(Date date, int weatherCode, double minTemperature, double maxTemperature, double sunshineDuration) {
        this.date = date;
        this.weatherCode = weatherCode;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.pvProduction = BigDecimal
                .valueOf(PV_POWER * sunshineDuration / 3600. * PV_EFFICIENCY)
                .setScale(1, RoundingMode.FLOOR).doubleValue();
    }

    public Date getDate() {
        return date;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getPvProduction() {
        return pvProduction;
    }
}
