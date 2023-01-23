package com.weather.model;


public class ForecastAdvice {

    private  String message;
    private  double temp_max;
    private  double temp_min;
    private  double wind_speed;
    private  double humidity;
    private String dt_value;

    public ForecastAdvice(String message, double temp_max, double temp_min, double wind_speed, double humidity, String dt_value) {
        this.message = message;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.wind_speed = wind_speed;
        this.humidity = humidity;
        this.dt_value = dt_value;
    }

    public ForecastAdvice() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getDt_value() {
        return dt_value;
    }

    public void setDt_value(String dt_value) {
        this.dt_value = dt_value;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }
}
