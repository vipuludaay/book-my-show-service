package org.vip.bookmyshow.services.city;

import org.vip.bookmyshow.dtos.CityResponseDto;

import java.util.List;

public interface CityService {
    public List<CityResponseDto> getAllCities();
}
