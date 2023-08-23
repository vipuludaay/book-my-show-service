package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.bookmyshow.models.City;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
}
