package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.bookmyshow.models.ShowSeatLock;

public interface ShowSeatLockRepository extends JpaRepository<ShowSeatLock, Long> {
}
