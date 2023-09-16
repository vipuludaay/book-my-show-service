package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vip.bookmyshow.models.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
