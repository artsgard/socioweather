package com.artsgard.socioweather;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author artsgard
 */
@RestController
@RequestMapping("")
public class WeatherController {

    org.slf4j.Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherExternalService service;
    
    @GetMapping(path = "/{city}", produces = "application/json")
    public ResponseEntity<?> getReport(@PathVariable String city) throws JSONException {
        WeatherDTO weather = service.getReport(city);
        if(weather != null) {
           return new ResponseEntity<>(weather, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping(path = "/cities", produces = "application/json")
    public ResponseEntity<List<String>> getAllCities() {
       List<String> list = new ArrayList();  
       return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
