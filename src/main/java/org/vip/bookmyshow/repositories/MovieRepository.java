package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.bookmyshow.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
