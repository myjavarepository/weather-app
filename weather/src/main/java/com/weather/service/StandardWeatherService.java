package com.weather.service;

import com.weather.model.ForecastResponse;
import com.weather.model.Weather;



public interface StandardWeatherService {

  ForecastResponse getCityWeatherForecast(String city);

}
