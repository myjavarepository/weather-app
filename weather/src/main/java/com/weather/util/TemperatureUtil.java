package com.weather.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TemperatureUtil {

    private  static  final BigDecimal MAX_VAL= BigDecimal.valueOf(273.15);
    private TemperatureUtil(){}

    public  static double kelvinToCelsius(double kelvinTemp)
    {

        BigDecimal val=BigDecimal.valueOf(kelvinTemp);

        return (val.subtract(MAX_VAL).setScale(2)).doubleValue();
    }

    public static String convertDateToString(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate().toString();
    }
}
