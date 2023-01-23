package com.weather.model;


import java.util.ArrayList;
import java.util.List;


public class Weather {

    private String cod;

    private long message;

    private long cnt;

    private List<WeatherObjectList> list = new ArrayList<>();
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public long getMessage() {
        return message;
    }

    public void setMessage(long message) {
        this.message = message;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public List<WeatherObjectList> getList() {
        return list;
    }

    public void setList(List<WeatherObjectList> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
