package com.example.weather.service;

import com.example.weather.model.Root;
import com.example.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository repository;

    @Cacheable(value = "weatherCache", key = "#lat + ':' + #lon")
    public Root getWeather(String lat, String lon) {
        validateCoordinates(lat, lon);

        try {
            Root root = repository.findByCoordinates(lat, lon);
            validateRoot(root);
            return root;
        } catch (RestClientException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Не удалось получить данные от OpenWeatherMap");
        }
    }

    private void validateCoordinates(String lat, String lon) {
        if (lat == null || lat.isBlank() || lon == null || lon.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Параметры lat и lon обязательны");
        }
    }

    private void validateRoot(Root root) {
        if (root == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "OpenWeatherMap вернул пустой ответ");
        }
        if (root.getMain() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "В ответе отсутствуют данные main");
        }
        if (root.getCoord() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "В ответе отсутствуют данные coord");
        }
    }
}
