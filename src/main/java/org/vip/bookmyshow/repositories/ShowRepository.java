package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.bookmyshow.models.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
}
