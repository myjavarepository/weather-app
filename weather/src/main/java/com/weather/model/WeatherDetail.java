package com.weather.model;


public class WeatherDetail {

    private float id;

    private String main;

    private String description;
    private String icon;

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
