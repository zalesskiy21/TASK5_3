package com.example.location.service;

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.weather.service}")
    private String weatherServiceUrl;

    public List<Location> findAll() {
        return repository.findAll();
    }

    public Location findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Location save(Location location) {
        return repository.save(location);
    }

    public Location update(String name, Location location) {
        Location existing = findByName(name);
        existing.setLongitude(location.getLongitude());
        existing.setLatitude(location.getLatitude());
        existing.setName(location.getName());
        return repository.save(existing);
    }

    public void delete(String name) {
        Location existing = findByName(name);
        repository.delete(existing);
    }

    public Weather getWeatherByLocationName(String name) {
        Location location = findByName(name);
        String url = String.format("%s?lat=%s&lon=%s",
                weatherServiceUrl, location.getLatitude(), location.getLongitude());
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        if (response == null || response.getMain() == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return response.getMain();
    }

    private static class WeatherResponse {
        private Weather main;

        public Weather getMain() {
            return main;
        }

        public void setMain(Weather main) {
            this.main = main;
        }
    }
}
