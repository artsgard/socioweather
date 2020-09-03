package com.artsgard.socioweather;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentMatchers.any;

/**
 *
 * @author WillemDragstra
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
public class SocioWeatherControllerTest {
    
    org.slf4j.Logger logger = LoggerFactory.getLogger(SocioWeatherController.class);

    @MockBean
    private SocioWeatherExternalService service;

    @Autowired
    private TestRestTemplate restTemplate;
    
    private SocioWeatherDTO socioWeather;

    @BeforeEach
    public void setup() {
        List<SocioWeatherDTO.WeatherType> tagList = new ArrayList();
        tagList.add(SocioWeatherDTO.WeatherType.COLD);
        tagList.add(SocioWeatherDTO.WeatherType.STORMY);
        socioWeather = new SocioWeatherDTO("description", "main", "temp", "tempMax", "tempMin", "humidity", "pressure", "clouds", "wind", "city", tagList);
    }
 
    @Test
    public void getReportTest()throws Exception {
       given(service.getReport(any(String.class)))
                .willReturn(socioWeather);

        ResponseEntity<SocioWeatherDTO> socioResponse = restTemplate
                .getForEntity("/London", SocioWeatherDTO.class);

        assertThat(socioResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(socioResponse.getBody().equals(socioWeather));
    }
}