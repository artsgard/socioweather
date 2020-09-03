package com.artsgard.socioweather;

import com.artsgard.socioweather.SocioWeatherController;
import com.artsgard.socioweather.SocioWeatherDTO;
import com.artsgard.socioweather.SocioWeatherExternalService;
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
import org.springframework.boot.test.json.JacksonTester;
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
    
    @Autowired
    private JacksonTester<SocioWeatherDTO> jsonSocioWeather;
    
    @Autowired
    private JacksonTester<List<SocioWeatherDTO>> jsonSociosWeather;
    
    private SocioWeatherDTO socioWeather;
    private List<SocioWeatherDTO> reports;

    @BeforeEach
    public void setup() {
        reports = new ArrayList();
       
    }
 
    @Test
    public void getReportTest()throws Exception {
       given(service.getReport(any(String.class)))
                .willReturn(socioWeather);

        ResponseEntity<SocioWeatherDTO[]> socioResponse = restTemplate
                .getForEntity("/London", SocioWeatherDTO[].class);

        assertThat(socioResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(socioResponse.getBody().equals(reports));
    }
}