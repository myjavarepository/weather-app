package com.weather.service.impl;

import com.weather.client.StandardWeatherClient;
import com.weather.constant.WeatherConstant;
import com.weather.model.*;
import com.weather.service.StandardWeatherService;
import com.weather.util.TemperatureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
    @Author: Pankaj K Singh

*/

@Service

public class StandardWeatherServiceImpl implements StandardWeatherService {
    @Autowired
   private StandardWeatherClient client;

    private  static  final Logger LOG= LoggerFactory.getLogger(StandardWeatherServiceImpl.class);


    @Override
    @Cacheable(value = "forecastList",key = "#city")
    public ForecastResponse getCityWeatherForecast(String city) {
        LOG.info("Getting weather forecast for city {}",city);
        return getWeatherForecastDetails(city);
    }



   private  ForecastResponse  getWeatherForecastDetails(String city)
   {

       Weather weather =client.getWeather(city);

       List<WeatherObjectList> weatherObjectList= weather.getList();

       List<ForecastAdvice> forecastAdvices=bindForecastResponse(weatherObjectList);

       //List<ForecastAdvice> forecastAdvices= getAverageDayTemperature(weatherObjectList);

       ForecastResponse response =new ForecastResponse();
       response.setAdviceList(forecastAdvices);
       response.setCity(city);
       return response;

   }

   private  List<ForecastAdvice> bindForecastResponse(List<WeatherObjectList> weatherObjectList)
   {
        return weatherObjectList.stream().map(weatherObj->{
          ForecastAdvice forecastAdvice =new ForecastAdvice();
          forecastAdvice.setMessage(getActionMessage(weatherObj.getMain()));
          forecastAdvice.setDt_value(weatherObj.getDt_txt());
          forecastAdvice.setHumidity(weatherObj.getMain().getHumidity());
          forecastAdvice.setWind_speed(weatherObj.getWind().getSpeed());
          forecastAdvice.setTemp_max(TemperatureUtil.kelvinToCelsius(weatherObj.getMain().getTemp_max()));
          forecastAdvice.setTemp_min(TemperatureUtil.kelvinToCelsius(weatherObj.getMain().getTemp_min()));
          return forecastAdvice;
         }).collect(Collectors.toList());
   }



   private  List<ForecastAdvice> getAverageDayTemperature(List<WeatherObjectList> weatherObjectList)
   {
       Map<String, List<ForecastAdvice>> forecastMap=new HashMap<>();
        for(WeatherObjectList weatherObj:weatherObjectList){
           String dateTime= weatherObj.getDt_txt();
            String key = getDateKey(dateTime);

            if(forecastMap.get(key)==null){
              forecastMap.put(key,new ArrayList<ForecastAdvice>());
          }
          forecastMap.get(key).add(new ForecastAdvice(getActionMessage(weatherObj.getMain()),
                  TemperatureUtil.kelvinToCelsius(weatherObj.getMain().getTemp_max())
                  ,TemperatureUtil.kelvinToCelsius(weatherObj.getMain().getTemp_min()),weatherObj.getWind().getSpeed(),
                  weatherObj.getMain().getHumidity(),weatherObj.getDt_txt()));
        }
          List<ForecastAdvice> adviceList=new ArrayList<>();

        for(Map.Entry<String, List<ForecastAdvice>> entry: forecastMap.entrySet()){

           ForecastAdvice advice= entry.getValue().stream().sorted(Comparator.comparing(ForecastAdvice::getTemp_max)
                   .thenComparing(ForecastAdvice::getHumidity).reversed()).findFirst().get();
                   adviceList.add(advice) ;
        }
        return adviceList;
   }

    private String getDateKey(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
           LOG.error("Date Parsing Error: " ,e);
        }

        return TemperatureUtil.convertDateToString(date);
    }

    private String getActionMessage(Main main)
   {
       if(TemperatureUtil.kelvinToCelsius(main.getTemp_max())>40.0)
       {
           return WeatherConstant.SUN_LOTION;
       }else if(main.getHumidity()>90)
       {
           return  WeatherConstant.TAKE_UMBRELLA;
       }else {
           return WeatherConstant.ENJOY_DAY;
       }
   }

    @CacheEvict(allEntries = true,cacheNames ={ "forecastList"})
    @Scheduled(fixedDelay = 1000*60)
       public  void evictForecastCache()
       {
            LOG.info("Forecast Cache Cleared...");
       }

}
