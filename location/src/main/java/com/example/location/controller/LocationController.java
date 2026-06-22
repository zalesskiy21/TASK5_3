package com.example.location.controller;

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> findAll() {
        return locationService.findAll();
    }

    @GetMapping(params = "name")
    public Location findByName(@RequestParam String name) {
        return locationService.findByName(name);
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return locationService.save(location);
    }

    @PutMapping(params = "name")
    public Location update(@RequestParam String name, @RequestBody Location location) {
        return locationService.update(name, location);
    }

    @DeleteMapping(params = "name")
    public void delete(@RequestParam String name) {
        locationService.delete(name);
    }

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam String name) {
        return locationService.getWeatherByLocationName(name);
    }
}
