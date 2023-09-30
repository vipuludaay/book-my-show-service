package org.vip.bookmyshow.services.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vip.bookmyshow.dtos.TicketResponseDto;
import org.vip.bookmyshow.exceptions.ShowNotAvailableException;
import org.vip.bookmyshow.exceptions.ShowSeatNotAvailableException;
import org.vip.bookmyshow.exceptions.UserNotFoundException;
import org.vip.bookmyshow.models.*;
import org.vip.bookmyshow.repositories.*;
import org.vip.bookmyshow.util.BMSConstants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    private ShowSeatRepository showSeatRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatLockRepository showSeatLockRepository;

    @Autowired
    public TicketServiceImpl(ShowSeatRepository showSeatRepository, UserRepository userRepository,
                             ShowRepository showRepository, ShowSeatLockRepository showSeatLockRepository) {
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatLockRepository = showSeatLockRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TicketResponseDto bookTicket(Long userId, List<Long> showSeatsIds, Long showId)
            throws ShowSeatNotAvailableException, UserNotFoundException, ShowNotAvailableException {
        TicketResponseDto ticketResponseDto = null;
        try {
            // 1. Validate User and Show.
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new UserNotFoundException("User with Id: " + userId + " not found!");
            }
            Optional<Show> show = showRepository.findByIdAndStartTimeAfter(showId, LocalDateTime.now());
            if (show.isEmpty()) {
                throw new ShowNotAvailableException("Show with Id: " + showId + " not available!");
            }

            // 2. Acquire db lock on all the passed showSeats only (not on whole table).
            // (FETCH WITH LOCK along with Serializable Isolation level in @Transaction)
            List<ShowSeat> showSeats = showSeatRepository.findAllByIdIn(showSeatsIds);

            // 3. Check if all passed showSeats are AVAILABLE or not. If not, throw an exception.
            validateAllShowSeatsAvailability(showSeats, showSeatsIds);

            // 4. If AVAILABLE, update showSeats status as "LOCKED". Then, insert showSeatLock for each showSeat.
            // "LOCKED" required to stop other users seeing these seats as AVAILABLE in BMS.
            // showSeats is Persistent data. Any change to showSeats persists automatically. saveAll() not required.
            showSeats.forEach(s -> s.setShowSeatStatus(ShowSeatStatus.LOCKED));

            // 5. Insert expiry time for each showSeats in a ShowSeatLock table. This time will be checked by
            // another service in every 5 minutes that if expiry < currentTime for any showSeats then
            // mark them from LOCKED to AVAILABLE. Then delete those records from ShowSeatLock table.
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(BMSConstants.TRANSACTION_EXPIRY_MINUTES);
            List<ShowSeatLock> showSeatLocks = showSeats.stream().map(showSeat -> new ShowSeatLock(showSeat, expiry)).toList();
            showSeatLockRepository.saveAll(showSeatLocks);

            // 6. Send temporary ticket object having show, showSeats info to client. This won't be visible to client.
            Ticket ticket = new Ticket();
            ticket.setBookedBy(user.get());
            ticket.setTicketStatus(TicketStatus.PENDING);
            ticket.setShow(show.get());
            ticket.setShowSeats(showSeats);

            ticketResponseDto = new TicketResponseDto();
            ticketResponseDto.setTicket(ticket);
            ticketResponseDto.setShowSeatLocks(showSeatLocks);

            // 7. Send ticketResponseDto to client. Get payment details and then proceed to Payment via PaymentService.
            // 8. If any one of partial payment is successful, then save Ticket object along with Payment Details.
            // If all partial payments are successful and all showSeatLockIds.expiry > curTime
            // then update ticket = BOOKED and all showSeats = BOOKED.
            // Else, rollback LOCKED for all showSeats in ticket object and update Ticket = FAILED.
            // Don't delete records from ShowSeatLock table. This will be taken care by another service as explained above.
        } catch (ShowSeatNotAvailableException | UserNotFoundException | ShowNotAvailableException e) {
            LOGGER.error("Error in TicketServiceImpl :: bookTicket :: " + e.getMessage());
            throw e;
        }
        return ticketResponseDto;
    }

    private void validateAllShowSeatsAvailability(List<ShowSeat> dbShowSeats, List<Long> userShowSeatIds)
            throws ShowSeatNotAvailableException {
        if (dbShowSeats.size() < userShowSeatIds.size()) {
            throw new ShowSeatNotAvailableException("Invalid Show Seats. Please try again!");
        }
        Optional<ShowSeat> showSeatUnavailable = dbShowSeats.stream()
                .filter(s -> s.getShowSeatStatus() != ShowSeatStatus.AVAILABLE).findFirst();
        if (showSeatUnavailable.isPresent()) {
            throw new ShowSeatNotAvailableException("Seat number " + showSeatUnavailable.get().getSeat().getSeatNumber()
                    + " not available. Try other available seats!");
        }
    }
}
