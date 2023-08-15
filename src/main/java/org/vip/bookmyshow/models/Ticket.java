package org.vip.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Ticket extends BaseModel {
    @OneToOne
    private Show show;

    @OneToMany
    private List<ShowSeat> showSeats;

    private Integer totalPrice;

    @OneToMany
    private List<Payment> payments;


    private LocalDateTime bookingTime;
    private TicketStatus ticketStatus;

    @ManyToOne
    private User bookedBy;
}
