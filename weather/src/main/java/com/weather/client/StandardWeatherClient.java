package com.weather.client;

import com.weather.model.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "weatherClient", url = "${feign.client.url}")
@Component
public interface StandardWeatherClient {

    @RequestMapping(method = RequestMethod.GET,value = "?q={city}&appid=d2929e9483efc82c82c32ee7e02d563e&cnt=10")
  //  @RequestMapping(method = RequestMethod.GET,value = "?q={city}&appid=d2929e9483efc82c82c32ee7e02d563e")
    Weather getWeather(@RequestParam(value = "city") String city);

}
