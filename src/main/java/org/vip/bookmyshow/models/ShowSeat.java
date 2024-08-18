package org.vip.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SHOW_SEAT_MAPPING")
public class ShowSeat extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "SHOW_ID")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "SEAT_ID")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_SEAT_STATUS")
    private ShowSeatStatus showSeatStatus;
}
