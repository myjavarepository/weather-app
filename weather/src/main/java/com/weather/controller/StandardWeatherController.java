package com.weather.controller;

import com.weather.error.InvalidCityException;
import com.weather.model.ForecastAdvice;
import com.weather.model.ForecastResponse;

import com.weather.service.StandardWeatherService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/weatherapi/v1")
public class StandardWeatherController {

    @Autowired
    private StandardWeatherService service;

    @GetMapping(value = "/forecast/{city}",produces = { "application/hal+json" })
    @CircuitBreaker(name = "forecastRetry", fallbackMethod = "hardcodedForecastResponse")
    public ResponseEntity<ForecastResponse> getWeatherForecast(@PathVariable("city") String city)
    {
        ForecastResponse weather=service.getCityWeatherForecast(city);

        //Hateoas config of link
        Link link = new Link("https://api.openweathermap.org/data/2.5/forecast?q=london&appid=d2929e9483efc82c82c32ee7e02d563e&cnt=10");
        weather.add(link);
        return new ResponseEntity<>(weather,HttpStatus.OK);
    }


    private   ResponseEntity<ForecastResponse> hardcodedForecastResponse(String city ,Exception exception)
    {
        if(city.length()<2){
            throw new InvalidCityException("Provided city "+city+" is not valid, please provide valid value !");
        }
        ForecastResponse advice= new ForecastResponse();
        advice.setCity("Something went wrong for city "+city+". Please contact admin !");

        return new ResponseEntity<>(advice,HttpStatus.OK);
    }
    

    
}
