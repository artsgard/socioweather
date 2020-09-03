package com.artsgard.socioweather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class SocioWeatherExternalServiceTest {

    @InjectMocks
    private SocioWeatherExternalService service;

    private SocioWeatherDTO weatherDTOMock;
   
    public static final Long NON_EXISTING_ID = 7000L;
    public static final String NON_EXISTING_USERNAME = "SDFSDFSFSDFSDF";

    @BeforeEach
    public void setup() {
      
    }

    @Test
    public void testGetWeatherTagList() {
        
    }
}