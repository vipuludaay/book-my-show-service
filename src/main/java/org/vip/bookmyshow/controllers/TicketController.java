package org.vip.bookmyshow.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vip.bookmyshow.dtos.TicketRequestDto;
import org.vip.bookmyshow.dtos.TicketResponseDto;
import org.vip.bookmyshow.exceptions.ShowNotAvailableException;
import org.vip.bookmyshow.exceptions.ShowSeatNotAvailableException;
import org.vip.bookmyshow.exceptions.UserNotFoundException;
import org.vip.bookmyshow.models.Ticket;
import org.vip.bookmyshow.services.ticket.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    private TicketService ticketService;

    @Autowired
    public TicketController(@Qualifier("ticketServiceImpl") TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("")
    public ResponseEntity<?> bookTicket(@RequestBody TicketRequestDto ticketRequestDto) {
        ResponseEntity<?> response = null;
        try {
            TicketResponseDto ticketResponseDto = ticketService.bookTicket(ticketRequestDto.getUserId(),
                    ticketRequestDto.getShowSeats(), ticketRequestDto.getShowId());
            response = ResponseEntity.ok(ticketResponseDto);
        } catch (ShowNotAvailableException | ShowSeatNotAvailableException e) {
            LOGGER.error("Error in TicketController :: bookTicket :: " + e.getMessage());
            response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (UserNotFoundException e) {
            LOGGER.error("Error in TicketController :: bookTicket :: " + e.getMessage());
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error in TicketController :: bookTicket :: " + e.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return response;
    }
}
