package org.vip.bookmyshow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vip.bookmyshow.dtos.CityResponseDto;
import org.vip.bookmyshow.services.city.CityService;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCities() {
        List<CityResponseDto> cities = cityService.getAllCities();
        return ResponseEntity.ok(cities);
    }
}
