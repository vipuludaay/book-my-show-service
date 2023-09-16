package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vip.bookmyshow.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
