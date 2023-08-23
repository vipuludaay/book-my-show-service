package org.vip.bookmyshow.dtos;

import lombok.Data;
import org.vip.bookmyshow.models.Ticket;

@Data
public class TicketResponseDto {
    private Ticket ticket;
}
