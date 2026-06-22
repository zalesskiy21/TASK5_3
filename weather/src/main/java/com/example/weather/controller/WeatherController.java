package com.example.weather.controller;

import com.example.weather.model.Root;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public Root getWeather(@RequestParam String lat, @RequestParam String lon) {
        return weatherService.getWeather(lat, lon);
    }
}
