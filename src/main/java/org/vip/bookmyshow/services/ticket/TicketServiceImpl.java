package org.vip.bookmyshow.services.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.vip.bookmyshow.exceptions.ShowNotAvailableException;
import org.vip.bookmyshow.exceptions.ShowSeatNotAvailableException;
import org.vip.bookmyshow.exceptions.UserNotFoundException;
import org.vip.bookmyshow.models.*;
import org.vip.bookmyshow.repositories.ShowRepository;
import org.vip.bookmyshow.repositories.ShowSeatRepository;
import org.vip.bookmyshow.repositories.TicketRepository;
import org.vip.bookmyshow.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    private ShowSeatRepository showSeatRepository;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;

    public TicketServiceImpl(ShowSeatRepository showSeatRepository, TicketRepository ticketRepository,
                             UserRepository userRepository, ShowRepository showRepository) {
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(Long userId, List<Long> showSeatsIds, Long showId)
            throws ShowSeatNotAvailableException, UserNotFoundException, ShowNotAvailableException {
        Ticket ticket = null;
        try {
            // 1. Fetch all passed showSeats from db. (FETCH WITHOUT LOCK)
            List<ShowSeat> showSeats = showSeatRepository.findAllByIdInWithoutLock(showSeatsIds);

            // 2. Check if all passed showSeats are AVAILABLE or not. If not, throw an exception.
            validateAllShowSeatsAvailable(showSeats);

            // 3. Acquire db lock on all the passed showSeats only (not on whole table). (FETCH WITH LOCK)
            showSeats = showSeatRepository.findAllByIdIn(showSeatsIds);

            // 4. Check for all AVAILABLE showSeats again. This is required for multiple threads that might have entered
            // step 2 at same time as of 1st thread that acquired lock initially. Double Check Locking.
            // If AVAILABLE, update showSeats status as "BLOCKED" (required to stop other users seeing these seats as AVAILABLE in BMS).
            // Else, throw an exception.
            validateAllShowSeatsAvailable(showSeats);
            showSeats.forEach(s -> s.setShowSeatStatus(ShowSeatStatus.BLOCKED));
            showSeatRepository.saveAll(showSeats);      // Check. May not be required as it's Persistent data.

            // 5. Generate Ticket(PENDING).
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new UserNotFoundException("User with Id " + userId + " not found!");
            }
            Optional<Show> show = showRepository.findById(showId);
            if (show.isEmpty()) {
                throw new ShowNotAvailableException("Show with Id " + show + " not found!");
            }
            ticket = new Ticket();
            ticket.setBookedBy(user.get());
            ticket.setTicketStatus(TicketStatus.PENDING);
            ticket.setBookingTime(LocalDateTime.now());
            ticket.setShow(show.get());
            ticket.setShowSeats(showSeats);
            ticket = ticketRepository.save(ticket);

            // 6. Proceed to Payment

            // 7. If payment successful, send Ticket(BOOKED) object. Else, rollback BLOCKED for showSeats, update Ticket(FAILED).
        } catch (ShowSeatNotAvailableException | UserNotFoundException | ShowNotAvailableException e) {
            LOGGER.error("Error in TicketServiceImpl :: bookTicket :: " + e.getMessage());
            throw e;
        }
        return ticket;
    }

    private void validateAllShowSeatsAvailable(List<ShowSeat> showSeats) throws ShowSeatNotAvailableException {
        Optional<ShowSeat> showSeatUnavailable = showSeats.stream()
                .filter(s -> s.getShowSeatStatus() != ShowSeatStatus.AVAILABLE).findFirst();
        if (showSeatUnavailable.isPresent()) {
            throw new ShowSeatNotAvailableException(showSeatUnavailable.get());
        }
    }
}
