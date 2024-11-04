package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TICKET")
public class Ticket extends BaseModel {
    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;

    @Column(name = "BOOKING_TIME")
    private LocalDateTime bookingTime;

    @OneToMany(mappedBy = "ticket")
    private List<Payment> payments;

    @Enumerated(EnumType.STRING)
    @Column(name = "TICKET_STATUS")
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn(name = "SHOW_ID")
    private Show show;

    // @ManyToMany because 1 Ticket can have many ShowSeat which is obvious.
    // 1 ShowSeat can be associated with many Ticket when a Ticket with a ShowSeat gets cancelled by a User
    // and another User books the same ShowSeat later. So same ShowSeat is associated with multiple Ticket.
    // In current version, partial Ticket cancellation not allowed. Either all ShowSeat in a Ticket get cancelled or none.
    @ManyToMany
    @JoinTable(name = "TICKET_SHOW_SEAT_MAPPING",
            joinColumns = @JoinColumn(name = "TICKET_ID"),
            inverseJoinColumns = @JoinColumn(name = "SHOW_SEAT_ID"))
    private List<ShowSeat> showSeats;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User bookedBy;
}
