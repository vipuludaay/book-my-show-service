package org.vip.bookmyshow.services.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.vip.bookmyshow.dtos.TicketResponseDto;
import org.vip.bookmyshow.exceptions.ShowNotAvailableException;
import org.vip.bookmyshow.exceptions.ShowSeatNotAvailableException;
import org.vip.bookmyshow.exceptions.UserNotFoundException;
import org.vip.bookmyshow.models.*;
import org.vip.bookmyshow.repositories.*;

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
            throws ShowSeatNotAvailableException, UserNotFoundException, ShowNotAvailableException, InterruptedException {
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

            // 2. Fetch all passed showSeats from db. (FETCH WITHOUT LOCK)
            List<ShowSeat> showSeats = showSeatRepository.findAllByIdInWithoutLock(showSeatsIds);

            // 3. Check if all passed showSeats are AVAILABLE or not. If not, throw an exception.
            validateAllShowSeatsAvailability(showSeats, showSeatsIds);

            // 4. Acquire db lock on all the passed showSeats only (not on whole table).
            // (FETCH WITH LOCK along with Serializable Isolation level in @Transaction)
            showSeats = showSeatRepository.findAllByIdIn(showSeatsIds);

            // 5. Check for all AVAILABLE showSeats again. If not AVAILABLE, throw an exception.
            // This is required for multiple threads that might have entered step 3 at same time
            // as of 1st thread that acquired lock initially. Double Check Locking.
            // showSeatRepository.saveAll(showSeats); not required here as showSeats are Persistent Entities.
            validateAllShowSeatsAvailability(showSeats, showSeatsIds);

            // 6. If AVAILABLE, update showSeats status as "LOCKED". Then, insert showSeatLock for each showSeat.
            // "LOCKED" required to stop other users seeing these seats as AVAILABLE in BMS.
            // showSeats is Persistent data. any change to showSeats persists automatically. saveAll() not required.
            showSeats.forEach(s -> s.setShowSeatStatus(ShowSeatStatus.LOCKED));
            showSeatRepository.saveAll(showSeats);

//        LocalDateTime expiry = LocalDateTime.now().plusMinutes(BMSConstants.TRANSACTION_EXPIRY_MINUTES);
//        List<ShowSeatLock> showSeatLocks = showSeats.stream().map(showSeat -> new ShowSeatLock(showSeat, expiry)).toList();
//        showSeatLockRepository.saveAll(showSeatLocks);

            Ticket ticket = new Ticket();
            ticket.setBookedBy(user.get());
            ticket.setTicketStatus(TicketStatus.PENDING);
            ticket.setBookingTime(LocalDateTime.now());
            ticket.setShow(show.get());
            ticket.setShowSeats(showSeats);

            ticketResponseDto = new TicketResponseDto();
            ticketResponseDto.setTicket(ticket);
//        ticketResponseDto.setShowSeatLocks(showSeatLocks);

            // 7. Send ticketResponseDto to User. Get payment details and then proceed to Payment via PaymentService.
            // 8. If any one of partial payment is successful, then save Ticket object along with Payment Details.
            // If all partial payments are successful and all showSeatLockIds.expiry > curTime,
            // then save all payment details, update ticket = BOOKED and all showSeats = BOOKED.
            // Else, rollback LOCKED for all showSeats in ticket object and update Ticket = FAILED.
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
