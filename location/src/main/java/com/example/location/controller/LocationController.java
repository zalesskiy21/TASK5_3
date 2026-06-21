package com.example.location.controller;

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.repository.LocationRepository;
import com.example.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> findAll() {
        return repository.findAll();
    }

    @GetMapping(params = "name")
    public Location findByName(@RequestParam String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }

    @PutMapping(params = "name")
    public Location update(@RequestParam String name, @RequestBody Location location) {
        Location existing = repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existing.setLongitude(location.getLongitude());
        existing.setLatitude(location.getLatitude());
        existing.setName(location.getName());
        return repository.save(existing);
    }

    @DeleteMapping(params = "name")
    public void delete(@RequestParam String name) {
        Location existing = repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.delete(existing);
    }

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam String name) {
        return locationService.getWeatherByLocationName(name);
    }
}
