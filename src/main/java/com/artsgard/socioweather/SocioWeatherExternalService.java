package com.artsgard.socioweather;

import com.artsgard.socioweather.exception.CityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author artsgard
 */
@Component
public class SocioWeatherExternalService {

    private org.slf4j.Logger logger;

    public SocioWeatherExternalService() {
        logger = LoggerFactory.getLogger(SocioWeatherExternalService.class);
    }

    private static final String URL_BASE = "http://api.openweathermap.org/data/2.5/weather?units=metric&lang=en&q=";
    private static final String TOKEN = "&APPID=5373a74b28442f4c6f5c69563b13dbb8";

    private SocioWeatherDTO dto;

    public SocioWeatherDTO getReport(String city) throws JSONException {
        BufferedReader br = null;
        StringBuilder sb = null;

        HttpURLConnection connection = null;
        try {
            String url = URL_BASE + city + TOKEN;
            connection = getConnection(url);
            br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String output;
            sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            System.out.println(sb.toString());
            JSONObject report = new JSONObject(sb.toString());
            JSONArray weatherArray = report.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            JSONObject main = report.getJSONObject("main");
            JSONObject wind = report.getJSONObject("wind");
            JSONObject clouds = report.getJSONObject("clouds");
            Object name = report.getString("name");

            dto = new SocioWeatherDTO();

            dto.setMain(weather.getString("main"));
            dto.setDescription(weather.getString("description"));
            dto.setTemp(main.getString("temp"));
            dto.setTempMax(main.getString("temp_max"));
            dto.setTempMin(main.getString("temp_min"));
            dto.setHumidity(main.getString("humidity"));
            dto.setPressure(main.getString("pressure"));
            dto.setClouds(clouds.getString("all"));
            dto.setCity(name.toString());
            dto.setWind(wind.getString("speed"));
            dto.setWeatherTypeTachs(getWeatherTagList(dto));

        } catch (IOException ex) {
            System.err.println("HttpURLConnection IOException: City not found! " + ex);
            logger.error("HttpURLConnection IOException: " + ex);
            throw new CityNotFoundException("No city found with the name: " + city);
        } finally {
            connection.disconnect();
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                System.err.println("Disconnect IOException: " + ex);
                logger.error("<Server IOException: " + ex);
            }
        }
        return dto;
    }

    private HttpURLConnection getConnection(String url) {
        try {
            URL serverAddress = new URL(url);

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("some proxy here", 8080));
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) serverAddress.openConnection(); //openConnection(proxy)
            } catch (IOException ex) {
                System.err.println("HttpURLConnection2 IOException: " + ex);
                logger.error("<Server IOException: " + ex);
                return null;
            }

            connection.setDoOutput(true);
            connection.setDoInput(true);
            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException ex) {
                System.err.println("RequestMethod GET IOException: " + ex);
                logger.error("<Server IOException: " + ex);
                return null;
            }
            connection.setRequestProperty("Accept", "application/json");
            connection.setReadTimeout(10000);
            try {
                connection.connect();
            } catch (IOException ex) {
                System.err.println("Connection Connect IOException: " + ex);
                logger.error("<Server IOException: " + ex);
                return null;
            }
            return connection;
        } catch (MalformedURLException ex) {
            System.err.println("MalformedURLException: " + ex);
            logger.error("<Server IOException: " + ex);
            return null;
        }
    }

    private List<SocioWeatherDTO.WeatherType> getWeatherTagList(SocioWeatherDTO weatherDto) {
        List<SocioWeatherDTO.WeatherType> tagList = new ArrayList();

        int temp = (int) Double.parseDouble(weatherDto.getTemp()); 
        if (temp < 0) {
            tagList.add(SocioWeatherDTO.WeatherType.FREEZING);
        } else if (temp  > 0 && temp < 18) {
            tagList.add(SocioWeatherDTO.WeatherType.COLD);
        } else if (temp  > 18 && temp < 23) {
            tagList.add(SocioWeatherDTO.WeatherType.WARM);
        } else if (temp  > 23) {
            tagList.add(SocioWeatherDTO.WeatherType.HOT);
        }
        
        String main = weatherDto.getMain();
        if (main.equalsIgnoreCase("Rain")) {
            tagList.add(SocioWeatherDTO.WeatherType.RAINY);
        } else if (main.equalsIgnoreCase("Clouds")) {
            tagList.add(SocioWeatherDTO.WeatherType.CLOUDY);
        } else if (main.equalsIgnoreCase("Clear")) {
            tagList.add(SocioWeatherDTO.WeatherType.CLEAR);
        } else if (main.equalsIgnoreCase("Snow")) {
            tagList.add(SocioWeatherDTO.WeatherType.SNOWING);
        } else if (main.contains("Sun")) {
            tagList.add(SocioWeatherDTO.WeatherType.SUNNY);
        } else if (main.equalsIgnoreCase("Mist")) {
            tagList.add(SocioWeatherDTO.WeatherType.MISTY);
        }
        
        double wind = Double.parseDouble(weatherDto.getWind()); 
        if (wind > 10 && wind > 20) {
            tagList.add(SocioWeatherDTO.WeatherType.WINDY);
        } else if (wind > 20 && wind > 30) {
            tagList.add(SocioWeatherDTO.WeatherType.STORMY);
        } else if (wind > 30) {
            tagList.add(SocioWeatherDTO.WeatherType.DANGEROUS_STORM);
        } 

        return tagList;
    }
}
