package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "TICKET")
public class Ticket extends BaseModel {
    @OneToOne
    @JoinColumn(name = "SHOW_ID")
    private Show show;

    @ManyToMany
    @JoinTable(name = "TICKET_SHOW_SEAT_MAPPING",
            joinColumns = @JoinColumn(name = "TICKET_ID"),
            inverseJoinColumns = @JoinColumn(name = "SHOW_SEAT_ID"))
    private List<ShowSeat> showSeats;

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;

    @OneToMany(mappedBy = "ticket")
    private List<Payment> payments;

    @Column(name = "BOOKING_TIME")
    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "TICKET_STATUS")
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User bookedBy;
}
