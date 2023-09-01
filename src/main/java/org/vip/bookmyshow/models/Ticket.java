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

    @ManyToMany
    @JoinTable(name = "TICKET_SHOW_SEAT_MAPPING",
            joinColumns = @JoinColumn(name = "TICKET_ID"),
            inverseJoinColumns = @JoinColumn(name = "SHOW_SEAT_ID"))
    private List<ShowSeat> showSeats;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User bookedBy;
}
