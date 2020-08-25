package com.artsgard.socioweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
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
public class WeatherExternalService {

    private org.slf4j.Logger logger;

    public WeatherExternalService() {
        logger = LoggerFactory.getLogger(WeatherExternalService.class);
    }

    private static final String URL_BASE = "http://api.openweathermap.org/data/2.5/weather?units=metric&lang=en&q=";
    private static final String TOKEN = "&APPID=5373a74b28442f4c6f5c69563b13dbb8";

    private WeatherDTO dto;

    public WeatherDTO getReport(String city) throws JSONException {
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

            JSONObject report = new JSONObject(sb.toString());
            JSONArray weatherArray = report.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            JSONObject main = report.getJSONObject("main");
            JSONObject wind = report.getJSONObject("wind");
            JSONObject clouds = report.getJSONObject("clouds");

            dto = new WeatherDTO();

            dto.setMain(weather.getString("main"));
            dto.setDescription(weather.getString("description"));
            dto.setTemp(main.getString("temp"));
            dto.setTempMax(main.getString("temp_max"));
            dto.setTempMin(main.getString("temp_min"));
            dto.setHumidity(main.getString("humidity"));
            dto.setPressure(main.getString("pressure"));
            dto.setClouds(clouds.getString("all"));
            dto.setWeatherType(WeatherDTO.WeatherType.WARM);

        } catch (IOException ex) {
            System.err.println("<Server IOException: " + ex);
            logger.error("<Server IOException: " + ex);
        } finally {
            connection.disconnect();
            try {

                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                System.err.println("<Server IOException: " + ex);
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
                logger.error("<Server IOException: " + ex);
                return null;
            }

            connection.setDoOutput(true);
            connection.setDoInput(true);
            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException ex) {
                logger.error("<Server IOException: " + ex);
                return null;
            }
            connection.setRequestProperty("Accept", "application/json");
            connection.setReadTimeout(10000);
            try {
                connection.connect();
            } catch (IOException ex) {
                logger.error("<Server IOException: " + ex);
                return null;
            }
            return connection;
        } catch (MalformedURLException ex) {
            logger.error("<Server IOException: " + ex);
            return null;
        }
    }
}
