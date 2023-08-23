package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.bookmyshow.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
