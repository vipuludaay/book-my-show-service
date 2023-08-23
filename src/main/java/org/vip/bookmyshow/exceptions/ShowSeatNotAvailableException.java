package org.vip.bookmyshow.exceptions;

import org.vip.bookmyshow.models.ShowSeat;

public class ShowSeatNotAvailableException extends Exception {
    public ShowSeatNotAvailableException(String message) {
        super(message);
    }

    public ShowSeatNotAvailableException(ShowSeat showSeat) {
        super("Seat number " + showSeat.getSeat().getSeatNumber() + " not available. Try other available seats!");
    }
}
