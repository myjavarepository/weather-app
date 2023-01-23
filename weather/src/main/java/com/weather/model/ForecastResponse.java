package com.weather.model;



import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class ForecastResponse extends RepresentationModel<ForecastResponse> {

    private  String city;
    private   List<ForecastAdvice> forecastDetails;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ForecastAdvice> getAdviceList() {
        return forecastDetails;
    }

    public void setAdviceList(List<ForecastAdvice> forecastDetails) {
        this.forecastDetails = forecastDetails;
    }
}
