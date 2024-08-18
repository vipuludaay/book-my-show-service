package org.vip.bookmyshow.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityResponseDto {
    private Long cityId;
    private String cityName;
}

