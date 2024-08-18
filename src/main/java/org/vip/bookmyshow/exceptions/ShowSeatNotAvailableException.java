package org.vip.bookmyshow.exceptions;

import org.vip.bookmyshow.models.ShowSeat;

public class ShowSeatNotAvailableException extends Exception {
    public ShowSeatNotAvailableException(String message) {
        super(message);
    }
}
