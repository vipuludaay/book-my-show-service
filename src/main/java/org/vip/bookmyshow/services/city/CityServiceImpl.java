package org.vip.bookmyshow.services.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vip.bookmyshow.dtos.CityResponseDto;
import org.vip.bookmyshow.models.City;
import org.vip.bookmyshow.repositories.CityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<CityResponseDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityResponseDto> cityResponseDtos = new ArrayList<>();
        for (City city: cities) {
            cityResponseDtos.add(new CityResponseDto(city.getId(), city.getName()));
        }
        return cityResponseDtos;
    }
}
