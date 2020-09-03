package com.artsgard.socioweather;

import com.artsgard.socioweather.exception.CityNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.codehaus.jettison.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class SocioWeatherExternalServiceTest {

    @InjectMocks
    private SocioWeatherExternalService service;
    
    @Mock
    HttpURLConnection connection;

    private SocioWeatherDTO socioWeather;
   
    @BeforeEach
    public void setup() {
        List<SocioWeatherDTO.WeatherType> tagList = new ArrayList();
        tagList.add(SocioWeatherDTO.WeatherType.COLD);
        tagList.add(SocioWeatherDTO.WeatherType.STORMY);
        socioWeather = new SocioWeatherDTO("description", "main", "temp", "tempMax", "tempMin", "humidity", "pressure", "clouds", "wind", "city", tagList);
    }

    //@Test
    public void testRetReport() throws JSONException {
       // URL serverAddress = new URL("someUrl");
       // given( new BufferedReader(new InputStreamReader((connection.getInputStream())))).willReturn(socioWeather); 
       given(service.getReport(any(String.class))).willReturn(socioWeather); 
       
    }
    
    @Test
    public void testRetReport_no_report() throws JSONException {
       given(service.getReport(any(String.class))).willReturn(null); 
        Assertions.assertThrows(CityNotFoundException.class, () -> {
            service.getReport(any(String.class));
        });
       
    }
    
    
    
    
}