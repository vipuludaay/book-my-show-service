package org.vip.bookmyshow.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TicketRequestDto {
    private List<Long> showSeats;
    private Long userId;
    private Long showId;
}
