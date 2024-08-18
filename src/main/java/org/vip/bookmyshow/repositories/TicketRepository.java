package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vip.bookmyshow.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
