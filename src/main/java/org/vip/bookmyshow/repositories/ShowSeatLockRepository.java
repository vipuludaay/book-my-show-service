package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vip.bookmyshow.models.ShowSeatLock;

@Repository
public interface ShowSeatLockRepository extends JpaRepository<ShowSeatLock, Long> {
}
