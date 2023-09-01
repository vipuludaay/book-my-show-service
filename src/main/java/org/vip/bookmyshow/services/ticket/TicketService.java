package org.vip.bookmyshow.services.ticket;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.vip.bookmyshow.dtos.TicketResponseDto;
import org.vip.bookmyshow.exceptions.ShowNotAvailableException;
import org.vip.bookmyshow.exceptions.ShowSeatNotAvailableException;
import org.vip.bookmyshow.exceptions.UserNotFoundException;
import org.vip.bookmyshow.models.Show;
import org.vip.bookmyshow.models.User;

import java.util.List;

public interface TicketService {
    TicketResponseDto bookTicket(Long userId, List<Long> showSeats, Long showId)
            throws ShowSeatNotAvailableException, UserNotFoundException, ShowNotAvailableException, InterruptedException;
}
