package org.vip.bookmyshow.dtos;

import lombok.Data;
import org.vip.bookmyshow.models.ShowSeatLock;
import org.vip.bookmyshow.models.Ticket;

import java.util.List;

@Data
public class TicketResponseDto {
    private Ticket ticket;
    private List<ShowSeatLock> showSeatLocks;
}
