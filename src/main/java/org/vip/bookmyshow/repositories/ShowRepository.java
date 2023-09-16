package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vip.bookmyshow.models.Show;
import org.vip.bookmyshow.models.ShowSeat;
import org.vip.bookmyshow.models.Ticket;
import org.vip.bookmyshow.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    Optional<Show> findByIdAndStartTimeAfter(Long showId, LocalDateTime curTime);
}
