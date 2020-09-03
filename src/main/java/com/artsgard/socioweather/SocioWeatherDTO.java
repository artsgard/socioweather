package com.artsgard.socioweather;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author artsgard
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SocioWeatherDTO {
    private String description;
    private String main;
    private String temp;
    private String tempMax;
    private String tempMin;
    private String humidity;
    private String pressure;
    private String clouds;
    private String city;
    
    public enum WeatherType {
        RAINY, CLOUDY, COULD, SUNNY, WARM, HOT, FREEZING
    }

    private List<WeatherType> weatherTypeTachs;
}
