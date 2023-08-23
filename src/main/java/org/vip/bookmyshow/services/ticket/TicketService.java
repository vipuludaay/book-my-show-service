package org.vip.bookmyshow.services.ticket;

import org.vip.bookmyshow.exceptions.ShowNotAvailableException;
import org.vip.bookmyshow.exceptions.ShowSeatNotAvailableException;
import org.vip.bookmyshow.exceptions.UserNotFoundException;
import org.vip.bookmyshow.models.ShowSeat;
import org.vip.bookmyshow.models.Ticket;

import java.util.List;

public interface TicketService {
    public Ticket bookTicket(Long userId, List<Long> showSeats, Long showId)
            throws ShowSeatNotAvailableException, UserNotFoundException, ShowNotAvailableException;
}
