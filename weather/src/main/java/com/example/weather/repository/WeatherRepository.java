package com.example.weather.repository;

import com.example.weather.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Repository
public class WeatherRepository {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    private String appId;

    @Value("${url.weather}")
    private String urlWeather;

    public Root findByCoordinates(String lat, String lon) {
        String url = String.format(
                Locale.US,
                "%s?lat=%s&lon=%s&units=metric&appid=%s",
                urlWeather, lat, lon, appId);
        return restTemplate.getForObject(url, Root.class);
    }
}
